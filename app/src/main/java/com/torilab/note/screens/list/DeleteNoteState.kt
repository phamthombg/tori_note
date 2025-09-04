package com.torilab.note.screens.list

sealed interface DeleteNoteState {
    object Success : DeleteNoteState
    data class Error(val message: String) : DeleteNoteState
}