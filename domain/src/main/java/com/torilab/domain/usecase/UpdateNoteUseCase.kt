package com.torilab.domain.usecase

import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository

class UpdateNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) = repository.updateNote(note)
}