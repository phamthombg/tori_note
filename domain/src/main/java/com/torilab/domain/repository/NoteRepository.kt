package com.torilab.domain.repository

import androidx.paging.PagingData
import com.torilab.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<PagingData<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun addNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)
}