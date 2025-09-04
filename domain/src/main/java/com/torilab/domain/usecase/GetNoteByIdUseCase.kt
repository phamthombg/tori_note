package com.torilab.domain.usecase

import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Long): Note? = repository.getNoteById(id)
}