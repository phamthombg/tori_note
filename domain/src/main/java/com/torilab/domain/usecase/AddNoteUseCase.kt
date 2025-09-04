package com.torilab.domain.usecase

import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note): Result<Long> {
        return try {
            Result.success(repository.addNote(note))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}