package com.torilab.domain.usecase

import com.torilab.domain.repository.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteNote(id)
}