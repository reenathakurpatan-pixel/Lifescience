package com.example.data

import android.content.Context
import com.example.R
import com.example.model.NoteTag
import com.example.model.PersonalNote
import com.example.model.TopicProgress
import com.example.model.TopicStatus
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StudySyncRepository(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth = Firebase.auth
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private fun requireUserId(expectedUserId: String): String {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("Please sign in with Google before saving study data.")
        check(uid == expectedUserId) { "Signed-in account changed; please retry." }
        return uid
    }

    fun observeTopicProgress(userId: String): Flow<List<TopicProgress>> = flow {
        val uid = requireUserId(userId)
        val path = "users/$uid/topicProgress"
        emitAll(
            db.collection("users").document(uid).collection("topicProgress")
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(
                        TopicProgress::class.java,
                        DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                    )
                }
                .catch { error ->
                    val firestoreError = generateSequence(error) { it.cause }
                        .filterIsInstance<FirebaseFirestoreException>()
                        .firstOrNull()
                    if (firestoreError == null && error is CancellationException) throw error
                    val toLog = firestoreError ?: (error as? Exception)
                    if (toLog != null && auth.currentUser?.uid == uid) {
                        handleFirestoreError(toLog, OperationType.LIST, path)
                    }
                    throw error
                }
        )
    }

    fun observePersonalNotes(userId: String): Flow<List<PersonalNote>> = flow {
        val uid = requireUserId(userId)
        val path = "users/$uid/notes"
        emitAll(
            db.collection("users").document(uid).collection("notes")
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(
                        PersonalNote::class.java,
                        DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                    )
                }
                .catch { error ->
                    val firestoreError = generateSequence(error) { it.cause }
                        .filterIsInstance<FirebaseFirestoreException>()
                        .firstOrNull()
                    if (firestoreError == null && error is CancellationException) throw error
                    val toLog = firestoreError ?: (error as? Exception)
                    if (toLog != null && auth.currentUser?.uid == uid) {
                        handleFirestoreError(toLog, OperationType.LIST, path)
                    }
                    throw error
                }
        )
    }

    suspend fun upsertTopicProgress(
        userId: String,
        topicId: String,
        unitNumber: Int,
        status: TopicStatus,
        bookmarked: Boolean
    ): Result<Unit> {
        val docPath = "users/$userId/topicProgress/$topicId"
        return try {
            val uid = requireUserId(userId)
            require(topicId.isNotBlank() && topicId.length <= 128) { "Invalid topic ID." }
            require(unitNumber in 1..13) { "Unit number must be between 1 and 13." }

            val docRef = db.collection("users").document(uid)
                .collection("topicProgress").document(topicId)
            val existingSnap = docRef.get().await()

            if (existingSnap.exists()) {
                docRef.update(
                    mapOf(
                        "status" to status.name,
                        "bookmarked" to bookmarked,
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            } else {
                docRef.set(
                    mapOf(
                        "userId" to uid,
                        "unitNumber" to unitNumber.toLong(),
                        "status" to status.name,
                        "bookmarked" to bookmarked,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "updatedAt" to FieldValue.serverTimestamp()
                    )
                ).await()
            }
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docPath)
            Result.failure(e)
        }
    }

    suspend fun createPersonalNote(
        userId: String,
        unitNumber: Int,
        topicId: String?,
        topicTitle: String?,
        title: String,
        content: String,
        tag: NoteTag
    ): Result<Unit> {
        var docPath = "users/$userId/notes"
        return try {
            val uid = requireUserId(userId)
            val cleanTitle = title.trim()
            val cleanContent = content.trim()
            require(unitNumber in 1..13) { "Unit must be between 1 and 13." }
            require(cleanTitle.isNotEmpty() && cleanTitle.length <= 120) {
                "Title must be between 1 and 120 characters."
            }
            require(cleanContent.isNotEmpty() && cleanContent.length <= 5000) {
                "Note content must be between 1 and 5000 characters."
            }

            val docRef = db.collection("users").document(uid).collection("notes").document()
            docPath = docRef.path

            val cleanTopicId = topicId?.trim()?.takeIf { it.isNotEmpty() }?.take(100)
            val cleanTopicTitle = topicTitle?.trim()?.takeIf { it.isNotEmpty() }?.take(160)

            val payload = mutableMapOf<String, Any>(
                "userId" to uid,
                "unitNumber" to unitNumber.toLong(),
                "title" to cleanTitle,
                "content" to cleanContent,
                "tag" to tag.name,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            if (cleanTopicId != null) payload["topicId"] = cleanTopicId
            if (cleanTopicTitle != null) payload["topicTitle"] = cleanTopicTitle

            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docPath)
            Result.failure(e)
        }
    }

    suspend fun updatePersonalNote(
        userId: String,
        noteId: String,
        unitNumber: Int,
        topicId: String?,
        topicTitle: String?,
        title: String,
        content: String,
        tag: NoteTag
    ): Result<Unit> {
        val docPath = "users/$userId/notes/$noteId"
        return try {
            val uid = requireUserId(userId)
            require(noteId.isNotBlank()) { "Note ID cannot be blank." }
            val cleanTitle = title.trim()
            val cleanContent = content.trim()
            require(unitNumber in 1..13) { "Unit must be between 1 and 13." }
            require(cleanTitle.isNotEmpty() && cleanTitle.length <= 120) {
                "Title must be between 1 and 120 characters."
            }
            require(cleanContent.isNotEmpty() && cleanContent.length <= 5000) {
                "Note content must be between 1 and 5000 characters."
            }

            val cleanTopicId = topicId?.trim()?.takeIf { it.isNotEmpty() }?.take(100)
            val cleanTopicTitle = topicTitle?.trim()?.takeIf { it.isNotEmpty() }?.take(160)

            val updates = mutableMapOf<String, Any>(
                "unitNumber" to unitNumber.toLong(),
                "title" to cleanTitle,
                "content" to cleanContent,
                "tag" to tag.name,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            if (cleanTopicId != null) {
                updates["topicId"] = cleanTopicId
            } else {
                updates["topicId"] = FieldValue.delete()
            }
            if (cleanTopicTitle != null) {
                updates["topicTitle"] = cleanTopicTitle
            } else {
                updates["topicTitle"] = FieldValue.delete()
            }

            db.collection("users").document(uid)
                .collection("notes").document(noteId)
                .update(updates)
                .await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    suspend fun deletePersonalNote(userId: String, noteId: String): Result<Unit> {
        val docPath = "users/$userId/notes/$noteId"
        return try {
            val uid = requireUserId(userId)
            require(noteId.isNotBlank()) { "Note ID cannot be blank." }
            db.collection("users").document(uid)
                .collection("notes").document(noteId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docPath)
            Result.failure(e)
        }
    }
}
