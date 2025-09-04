package com.torilab.note.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torilab.domain.model.Note
import com.torilab.domain.usecase.NoteUseCases
import com.torilab.note.screens.list.DeleteNoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val useCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteState = MutableStateFlow(NoteUiState())
    val noteState = _noteState.asStateFlow()

    private var _addUpdateNoteState = Channel<AddUpdateNoteState>(Channel.BUFFERED)
    val addUpdateNoteState = _addUpdateNoteState.receiveAsFlow()

    private var _deleteNoteState = Channel<DeleteNoteState>(Channel.BUFFERED)
    val deleteNoteState = _deleteNoteState.receiveAsFlow()

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
     * add a note
     */
    fun addNote() {
        viewModelScope.launch {
            val note = Note(
                id = _noteState.value.id,
                title = _noteState.value.title,
                content = _noteState.value.content
            )
            try {
                useCases.addNote(note)
                _addUpdateNoteState.send(AddUpdateNoteState.Success(note))
            } catch (ex: Exception) {
                _addUpdateNoteState.send(AddUpdateNoteState.Error(ex.message ?: ""))
            }
        }
    }

    /**
     * update note
     */
    fun updateNote() {
        viewModelScope.launch {
            val note = Note(
                id = _noteState.value.id,
                title = _noteState.value.title,
                content = _noteState.value.content
            )
            try {
                useCases.updateNote(note)
                _addUpdateNoteState.send(AddUpdateNoteState.Success(note))
            } catch (ex: Exception) {
                _addUpdateNoteState.send(AddUpdateNoteState.Error(ex.message ?: ""))
            }
        }
    }

    /**
     * delete a note by ID's Note
     */
    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            try {
                useCases.deleteNote(noteId)
                _deleteNoteState.send(DeleteNoteState.Success)
            } catch (ex: Exception) {
                _deleteNoteState.send(DeleteNoteState.Error(ex.message ?: ""))
            }
        }
    }
}