package com.torilab.domain.usecase

import androidx.paging.PagingData
import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(): Flow<PagingData<Note>> = repository.getNotes()
}