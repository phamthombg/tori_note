package com.torilab.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val getNoteById: GetNoteByIdUseCase,
    val addNote: AddNoteUseCase,
    val updateNote: UpdateNoteUseCase,
    val deleteNote: DeleteNoteUseCase
)
