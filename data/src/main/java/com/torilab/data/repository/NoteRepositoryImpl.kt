package com.torilab.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.torilab.data.local.NoteDao
import com.torilab.data.mapper.toDomain
import com.torilab.data.mapper.toEntity
import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<PagingData<Note>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { noteDao.getNotes() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
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

    companion object {
        const val PAGE_SIZE = 20
    }
}