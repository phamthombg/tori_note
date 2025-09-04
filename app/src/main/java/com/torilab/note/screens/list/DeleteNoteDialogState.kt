package com.torilab.note.screens.list

sealed interface DeleteNoteDialogState {
    data class Show(val noteId: Long): DeleteNoteDialogState
    object Hide: DeleteNoteDialogState
}