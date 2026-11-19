package com.example.data

import com.example.model.NoteTag
import com.example.model.PersonalNote
import com.example.model.TopicProgress
import com.example.model.TopicStatus
import java.util.UUID
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalStudyRepository(
    private val dao: StudyDao
) {
    fun observeTopicProgress(): Flow<List<TopicProgress>> =
        dao.observeTopicProgress().map { entities ->
            entities.map { entity ->
                TopicProgress(
                    id = entity.topicId,
                    unitNumber = entity.unitNumber,
                    status = entity.status,
                    bookmarked = entity.bookmarked,
                    updatedAtMillis = entity.updatedAtMillis
                )
            }
        }

    fun observePersonalNotes(): Flow<List<PersonalNote>> =
        dao.observePersonalNotes().map { entities ->
            entities.map { entity ->
                PersonalNote(
                    id = entity.id,
                    unitNumber = entity.unitNumber,
                    topicId = entity.topicId,
                    topicTitle = entity.topicTitle,
                    title = entity.title,
                    content = entity.content,
                    tag = entity.tag,
                    createdAtMillis = entity.createdAtMillis,
                    updatedAtMillis = entity.updatedAtMillis
                )
            }
        }

    suspend fun upsertTopicProgress(
        topicId: String,
        unitNumber: Int,
        status: TopicStatus,
        bookmarked: Boolean
    ): Result<Unit> = try {
        require(topicId.isNotBlank()) { "Invalid topic ID." }
        require(unitNumber in 1..13) { "Unit number must be between 1 and 13." }
        dao.upsertTopicProgress(
            TopicProgressEntity(
                topicId = topicId,
                unitNumber = unitNumber,
                status = status.name,
                bookmarked = bookmarked,
                updatedAtMillis = System.currentTimeMillis()
            )
        )
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun createPersonalNote(
        unitNumber: Int,
        topicId: String?,
        topicTitle: String?,
        title: String,
        content: String,
        tag: NoteTag
    ): Result<Unit> = try {
        val cleanTitle = title.trim()
        val cleanContent = content.trim()
        require(unitNumber in 1..13) { "Unit must be between 1 and 13." }
        require(cleanTitle.isNotEmpty() && cleanTitle.length <= 120) {
            "Title must be between 1 and 120 characters."
        }
        require(cleanContent.isNotEmpty() && cleanContent.length <= 5000) {
            "Note content must be between 1 and 5000 characters."
        }
        val now = System.currentTimeMillis()
        dao.upsertPersonalNote(
            PersonalNoteEntity(
                id = UUID.randomUUID().toString(),
                unitNumber = unitNumber,
                topicId = topicId?.trim()?.takeIf { it.isNotEmpty() }?.take(100),
                topicTitle = topicTitle?.trim()?.takeIf { it.isNotEmpty() }?.take(160),
                title = cleanTitle,
                content = cleanContent,
                tag = tag.name,
                createdAtMillis = now,
                updatedAtMillis = now
            )
        )
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun updatePersonalNote(
        noteId: String,
        unitNumber: Int,
        topicId: String?,
        topicTitle: String?,
        title: String,
        content: String,
        tag: NoteTag
    ): Result<Unit> = try {
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
        val now = System.currentTimeMillis()
        val existingCreatedAt = dao.getNoteById(noteId)?.createdAtMillis ?: now
        dao.upsertPersonalNote(
            PersonalNoteEntity(
                id = noteId,
                unitNumber = unitNumber,
                topicId = topicId?.trim()?.takeIf { it.isNotEmpty() }?.take(100),
                topicTitle = topicTitle?.trim()?.takeIf { it.isNotEmpty() }?.take(160),
                title = cleanTitle,
                content = cleanContent,
                tag = tag.name,
                createdAtMillis = existingCreatedAt,
                updatedAtMillis = now
            )
        )
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun deletePersonalNote(noteId: String): Result<Unit> = try {
        require(noteId.isNotBlank()) { "Note ID cannot be blank." }
        dao.deletePersonalNoteById(noteId)
        Result.success(Unit)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}
