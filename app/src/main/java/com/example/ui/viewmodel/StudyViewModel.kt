package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CsirSyllabusCatalog
import com.example.data.LocalStudyRepository
import com.example.data.ReaderPreferencesRepository
import com.example.model.AppThemeMode
import com.example.model.NoteTag
import com.example.model.PersonalNote
import com.example.model.ReaderFontScale
import com.example.model.TopicProgress
import com.example.model.TopicStatus
import com.example.model.UiState
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
    private val studyRepository: LocalStudyRepository,
    private val prefsRepository: ReaderPreferencesRepository
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
        studyRepository.observeTopicProgress()
            .map<List<TopicProgress>, UiState<Map<String, TopicProgress>>> { list ->
                UiState.Success(list.associateBy { it.id })
            }
            .catch { error ->
                if (error is CancellationException) throw error
                Log.w(TAG, "Error loading topic progress", error)
                emit(UiState.Error(error.message ?: "Failed to load study progress"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
                initialValue = UiState.Success(emptyMap())
            )

    val notesState: StateFlow<UiState<List<PersonalNote>>> =
        studyRepository.observePersonalNotes()
            .map<List<PersonalNote>, UiState<List<PersonalNote>>> { list ->
                UiState.Success(list)
            }
            .catch { error ->
                if (error is CancellationException) throw error
                Log.w(TAG, "Error loading personal notes", error)
                emit(UiState.Error(error.message ?: "Failed to load personal notes"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
                initialValue = UiState.Success(emptyList())
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
            val result = studyRepository.upsertTopicProgress(
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
            val result = studyRepository.upsertTopicProgress(
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
                studyRepository.createPersonalNote(
                    unitNumber = unitNumber,
                    topicId = topicId,
                    topicTitle = topicTitle,
                    title = title,
                    content = content,
                    tag = tag
                )
            } else {
                studyRepository.updatePersonalNote(
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
            val result = studyRepository.deletePersonalNote(noteId)
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
