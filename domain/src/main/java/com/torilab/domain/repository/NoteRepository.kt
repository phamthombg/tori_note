package com.torilab.domain.repository

import com.torilab.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun addNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)
}