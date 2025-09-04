package com.torilab.domain.usecase

import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(): Flow<List<Note>> = repository.getNotes()
}