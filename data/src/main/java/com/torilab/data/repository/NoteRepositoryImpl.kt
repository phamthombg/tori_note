package com.torilab.data.repository

import com.torilab.data.local.NoteDao
import com.torilab.data.mapper.toDomain
import com.torilab.data.mapper.toEntity
import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteDao.getById(id)?.toDomain()
    }

    override suspend fun addNote(note: Note): Long {
        return noteDao.insert(note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.update(note.toEntity())
    }

    override suspend fun deleteNote(id: Long) {
        noteDao.deleteById(id)
    }
}