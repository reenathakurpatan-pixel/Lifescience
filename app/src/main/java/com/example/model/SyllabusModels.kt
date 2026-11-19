package com.example.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

data class CsirUnit(
    val unitNumber: Int,
    val title: String,
    val shortTitle: String,
    val partFocus: String,
    val weightagePercent: Int,
    val accentHex: Long,
    val summary: String,
    val topics: List<StudyTopic>
)

data class StudyTopic(
    val id: String,
    val unitNumber: Int,
    val title: String,
    val subtitle: String,
    val highYield: Boolean,
    val estimatedMinutes: Int,
    val overview: String,
    val sections: List<ConceptSection>,
    val comparisonTable: StudyTable? = null,
    val keyFormulas: List<String> = emptyList(),
    val pyqPointers: List<String> = emptyList()
)

data class ConceptSection(
    val heading: String,
    val bullets: List<String>
)

data class StudyTable(
    val title: String,
    val headers: List<String>,
    val rows: List<List<String>>
)

enum class TopicStatus(val label: String) {
    NOT_STARTED("To Study"),
    REVISING("Revising"),
    MASTERED("Mastered");

    companion object {
        fun fromRaw(raw: String?): TopicStatus {
            return entries.firstOrNull { it.name == raw } ?: NOT_STARTED
        }
    }
}

enum class NoteTag(val label: String) {
    MNEMONIC("Mnemonic"),
    PYQ_TRAP("PYQ Trap"),
    FORMULA("Formula"),
    REVISION("Revision");

    companion object {
        fun fromRaw(raw: String?): NoteTag {
            return entries.firstOrNull { it.name == raw } ?: REVISION
        }
    }
}

@Keep
@IgnoreExtraProperties
data class TopicProgress(
    @DocumentId val id: String = "",
    val userId: String = "",
    val unitNumber: Long = 1L,
    val status: String = TopicStatus.NOT_STARTED.name,
    val bookmarked: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    fun topicStatus(): TopicStatus = TopicStatus.fromRaw(status)
}

@Keep
@IgnoreExtraProperties
data class PersonalNote(
    @DocumentId val id: String = "",
    val userId: String = "",
    val unitNumber: Long = 1L,
    val topicId: String? = null,
    val topicTitle: String? = null,
    val title: String = "",
    val content: String = "",
    val tag: String = NoteTag.REVISION.name,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    fun noteTag(): NoteTag = NoteTag.fromRaw(tag)
}

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}

enum class ReaderFontScale(val label: String, val multiplier: Float) {
    COMPACT("Compact", 0.9f),
    COMFORTABLE("Comfortable", 1.0f),
    LARGE("Large", 1.15f);

    companion object {
        fun fromRaw(raw: String?): ReaderFontScale {
            return entries.firstOrNull { it.name == raw } ?: COMFORTABLE
        }
    }
}

enum class AppThemeMode(val label: String) {
    SYSTEM("System"),
    LIGHT("Paper Light"),
    DARK("Night Lab");

    companion object {
        fun fromRaw(raw: String?): AppThemeMode {
            return entries.firstOrNull { it.name == raw } ?: SYSTEM
        }
    }
}
