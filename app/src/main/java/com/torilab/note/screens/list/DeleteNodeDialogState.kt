package com.torilab.note.screens.list

sealed interface DeleteNodeDialogState {
    data class Show(val noteId: Long): DeleteNodeDialogState
    object Hide: DeleteNodeDialogState
}