package com.torilab.domain.usecase

import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note): Long = repository.addNote(note)
}