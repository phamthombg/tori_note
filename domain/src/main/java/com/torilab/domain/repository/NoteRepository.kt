package com.torilab.domain.repository

import androidx.paging.PagingData
import com.torilab.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    /**
     * get notes using PagingData
     * @return Flow<PagingData<Note>>
     */
    fun getNotes(): Flow<PagingData<Note>>

    /**
     * get Note by ID
     * @param id: note id
     * @return Note result
     */
    suspend fun getNoteById(id: Long): Note?

    /**
     * add a new note
     * @param note: new note
     */
    suspend fun addNote(note: Note): Long

    /**
     * Update an exist node
     * @param note: update node
     */
    suspend fun updateNote(note: Note)

    /**
     * Delete a note
     * @param id: note id
     */
    suspend fun deleteNote(id: Long)
}