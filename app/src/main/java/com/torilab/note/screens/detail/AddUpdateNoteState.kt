package com.torilab.note.screens.detail

import com.torilab.domain.model.Note

sealed interface AddUpdateNoteState {
    data class Success(val note: Note) : AddUpdateNoteState
    data class Error(val message: String) : AddUpdateNoteState
}