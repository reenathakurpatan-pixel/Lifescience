package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "topic_progress")
data class TopicProgressEntity(
    @PrimaryKey val topicId: String,
    val unitNumber: Int,
    val status: String,
    val bookmarked: Boolean,
    val updatedAtMillis: Long
)

@Entity(tableName = "personal_notes")
data class PersonalNoteEntity(
    @PrimaryKey val id: String,
    val unitNumber: Int,
    val topicId: String?,
    val topicTitle: String?,
    val title: String,
    val content: String,
    val tag: String,
    val createdAtMillis: Long,
    val updatedAtMillis: Long
)

@Dao
interface StudyDao {
    @Query("SELECT * FROM topic_progress")
    fun observeTopicProgress(): Flow<List<TopicProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTopicProgress(entity: TopicProgressEntity)

    @Query("SELECT * FROM personal_notes ORDER BY updatedAtMillis DESC")
    fun observePersonalNotes(): Flow<List<PersonalNoteEntity>>

    @Query("SELECT * FROM personal_notes WHERE id = :noteId LIMIT 1")
    suspend fun getNoteById(noteId: String): PersonalNoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPersonalNote(entity: PersonalNoteEntity)

    @Query("DELETE FROM personal_notes WHERE id = :noteId")
    suspend fun deletePersonalNoteById(noteId: String)
}

@Database(
    entities = [TopicProgressEntity::class, PersonalNoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StudyDatabase : RoomDatabase() {
    abstract fun studyDao(): StudyDao

    companion object {
        @Volatile
        private var INSTANCE: StudyDatabase? = null

        fun getInstance(context: Context): StudyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StudyDatabase::class.java,
                    "bioprep_csir_local.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
