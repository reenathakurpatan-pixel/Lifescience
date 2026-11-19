package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CsirSyllabusCatalog
import com.example.data.ReaderPreferencesRepository
import com.example.data.StudySyncRepository
import com.example.model.AppThemeMode
import com.example.model.NoteTag
import com.example.model.PersonalNote
import com.example.model.ReaderFontScale
import com.example.model.TopicProgress
import com.example.model.TopicStatus
import com.example.model.UiState
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StudyViewModel(
    private val syncRepository: StudySyncRepository,
    private val prefsRepository: ReaderPreferencesRepository,
    private val currentUserId: String
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedNoteTag = MutableStateFlow<NoteTag?>(null)
    val selectedNoteTag: StateFlow<NoteTag?> = _selectedNoteTag.asStateFlow()

    private val _toastEvents = MutableSharedFlow<String>(extraBufferCapacity = 4)
    val toastEvents: SharedFlow<String> = _toastEvents.asSharedFlow()

    val fontScale: StateFlow<ReaderFontScale> = prefsRepository.fontScale
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = ReaderFontScale.COMFORTABLE
        )

    val themeMode: StateFlow<AppThemeMode> = prefsRepository.themeMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = AppThemeMode.SYSTEM
        )

    val progressState: StateFlow<UiState<Map<String, TopicProgress>>> =
        syncRepository.observeTopicProgress(currentUserId)
            .map<List<TopicProgress>, UiState<Map<String, TopicProgress>>> { list ->
                UiState.Success(list.associateBy { it.id })
            }
            .catch { error ->
                val firestoreError = generateSequence(error) { it.cause }
                    .filterIsInstance<FirebaseFirestoreException>()
                    .firstOrNull()
                if (firestoreError == null && error is CancellationException) throw error
                val rootError = firestoreError ?: error
                Log.w(TAG, "Error observing topic progress", rootError)
                emit(UiState.Error(rootError.message ?: "Failed to sync study progress"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
                initialValue = UiState.Loading
            )

    val notesState: StateFlow<UiState<List<PersonalNote>>> =
        syncRepository.observePersonalNotes(currentUserId)
            .map<List<PersonalNote>, UiState<List<PersonalNote>>> { list ->
                val sorted = list.sortedByDescending {
                    it.updatedAt ?: it.createdAt ?: Timestamp(0, 0)
                }
                UiState.Success(sorted)
            }
            .catch { error ->
                val firestoreError = generateSequence(error) { it.cause }
                    .filterIsInstance<FirebaseFirestoreException>()
                    .firstOrNull()
                if (firestoreError == null && error is CancellationException) throw error
                val rootError = firestoreError ?: error
                Log.w(TAG, "Error observing personal notes", rootError)
                emit(UiState.Error(rootError.message ?: "Failed to sync personal notes"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
                initialValue = UiState.Loading
            )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectNoteTag(tag: NoteTag?) {
        _selectedNoteTag.value = if (_selectedNoteTag.value == tag) null else tag
    }

    fun toggleBookmark(topicId: String) {
        val topic = CsirSyllabusCatalog.findTopic(topicId) ?: return
        val currentMap = (progressState.value as? UiState.Success)?.data.orEmpty()
        val existing = currentMap[topicId]
        val nextBookmarked = !(existing?.bookmarked ?: false)
        val currentStatus = existing?.topicStatus() ?: TopicStatus.NOT_STARTED

        viewModelScope.launch {
            val result = syncRepository.upsertTopicProgress(
                userId = currentUserId,
                topicId = topicId,
                unitNumber = topic.unitNumber,
                status = currentStatus,
                bookmarked = nextBookmarked
            )
            result.onSuccess {
                _toastEvents.tryEmit(
                    if (nextBookmarked) "Bookmarked for revision" else "Removed from bookmarks"
                )
            }.onFailure { err ->
                _toastEvents.tryEmit(err.localizedMessage ?: "Failed to update bookmark")
            }
        }
    }

    fun setTopicStatus(topicId: String, newStatus: TopicStatus) {
        val topic = CsirSyllabusCatalog.findTopic(topicId) ?: return
        val currentMap = (progressState.value as? UiState.Success)?.data.orEmpty()
        val existing = currentMap[topicId]
        val currentBookmarked = existing?.bookmarked ?: false

        viewModelScope.launch {
            val result = syncRepository.upsertTopicProgress(
                userId = currentUserId,
                topicId = topicId,
                unitNumber = topic.unitNumber,
                status = newStatus,
                bookmarked = currentBookmarked
            )
            result.onSuccess {
                _toastEvents.tryEmit("Marked as ${newStatus.label}")
            }.onFailure { err ->
                _toastEvents.tryEmit(err.localizedMessage ?: "Failed to update status")
            }
        }
    }

    fun saveNote(
        existingNoteId: String?,
        unitNumber: Int,
        topicId: String?,
        topicTitle: String?,
        title: String,
        content: String,
        tag: NoteTag
    ) {
        viewModelScope.launch {
            val result = if (existingNoteId.isNullOrBlank()) {
                syncRepository.createPersonalNote(
                    userId = currentUserId,
                    unitNumber = unitNumber,
                    topicId = topicId,
                    topicTitle = topicTitle,
                    title = title,
                    content = content,
                    tag = tag
                )
            } else {
                syncRepository.updatePersonalNote(
                    userId = currentUserId,
                    noteId = existingNoteId,
                    unitNumber = unitNumber,
                    topicId = topicId,
                    topicTitle = topicTitle,
                    title = title,
                    content = content,
                    tag = tag
                )
            }
            result.onSuccess {
                _toastEvents.tryEmit(
                    if (existingNoteId.isNullOrBlank()) "Note saved" else "Note updated"
                )
            }.onFailure { err ->
                _toastEvents.tryEmit(err.localizedMessage ?: "Could not save note")
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            val result = syncRepository.deletePersonalNote(currentUserId, noteId)
            result.onSuccess {
                _toastEvents.tryEmit("Note deleted")
            }.onFailure { err ->
                _toastEvents.tryEmit(err.localizedMessage ?: "Could not delete note")
            }
        }
    }

    fun setFontScale(scale: ReaderFontScale) {
        viewModelScope.launch {
            prefsRepository.setFontScale(scale)
        }
    }

    fun setThemeMode(mode: AppThemeMode) {
        viewModelScope.launch {
            prefsRepository.setThemeMode(mode)
        }
    }

    private companion object {
        const val TAG = "StudyVM"
        const val STOP_TIMEOUT_MILLIS = 5000L
    }
}
