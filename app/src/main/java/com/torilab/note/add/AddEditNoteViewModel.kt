package com.torilab.note.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torilab.domain.model.Note
import com.torilab.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val useCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteUiState())
    val noteState = _noteState.asStateFlow()

    init {
        val noteId = savedStateHandle.get<String>("noteId")?.toLongOrNull()
        if (noteId != null) {
            viewModelScope.launch {
                val note = useCases.getNoteById(noteId)
                note?.let {
                    _noteState.value = NoteUiState(
                        id = it.id,
                        title = it.title,
                        content = it.content
                    )
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _noteState.value = _noteState.value.copy(title = title)
    }

    fun updateContent(content: String) {
        _noteState.value = _noteState.value.copy(content = content)
    }

    fun saveNote() {
        viewModelScope.launch {
            val note = Note(
                id = _noteState.value.id,
                title = _noteState.value.title,
                content = _noteState.value.content
            )
            useCases.addNote(note)
        }
    }
}