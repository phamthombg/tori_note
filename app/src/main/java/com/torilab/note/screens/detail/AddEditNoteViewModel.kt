package com.torilab.note.screens.detail

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
                // get node by noteId from repository
                val note = useCases.getNoteById(noteId)
                note?.let { // if note is exist
                    // create a note UI state from note
                    _noteState.value = NoteUiState(
                        id = it.id,
                        title = it.title,
                        content = it.content
                    )
                }
            }
        }
    }

    /**
     * Update Note title
     */
    fun updateTitle(title: String) {
        _noteState.value = _noteState.value.copy(title = title)
    }

    /**
     * update Note content
     */
    fun updateContent(content: String) {
        _noteState.value = _noteState.value.copy(content = content)
    }

    /**
     * Check input is valid or not
     * @return true if title and content of note is not empty
     */
    fun isValid(): Boolean = _noteState.value.run {
        this.title.isNotEmpty() && this.content.isNotEmpty()
    }

    /**
     * check add new note or update note?
     * @return true if new note, false in remaining case
     */
    fun isAddNewNote() = _noteState.value.id == 0L

    /**
     * save a note
     */
    fun saveNote() {
        viewModelScope.launch {
            val note = Note(
                id = _noteState.value.id,
                title = _noteState.value.title,
                content = _noteState.value.content
            )
            if (_noteState.value.id == 0L) {
                useCases.addNote(note)
            } else {
                useCases.updateNote(note)
            }
        }
    }

    /**
     * delete a note by ID's Note
     */
    fun deleteNote(noteId: Long) {
        viewModelScope.launch { useCases.deleteNote(noteId) }
    }
}