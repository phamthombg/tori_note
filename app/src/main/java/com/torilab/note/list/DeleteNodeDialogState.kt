package com.torilab.note.list

sealed interface DeleteNodeDialogState {
    data class Show(val noteId: Long): DeleteNodeDialogState
    object Hide: DeleteNodeDialogState
}