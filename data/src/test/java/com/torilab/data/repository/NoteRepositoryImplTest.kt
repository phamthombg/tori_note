package com.torilab.data.repository

import com.torilab.data.local.NoteDao
import com.torilab.data.local.NoteEntity
import com.torilab.domain.model.Note
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteRepositoryImplTest {
    private lateinit var dao: NoteDao
    private lateinit var repository: NoteRepositoryImpl
    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = NoteRepositoryImpl(dao)
    }

    @Test
    fun getNoteById() = runTest(testDispatcher) {
        val entity = NoteEntity(1L, "Title", "Content", createdAt = 1L, updatedAt = 1L)
        coEvery { dao.getById(1L) } returns entity

        val note = repository.getNoteById(1L)

        assertNotNull(note)
        assertEquals("Title", note?.title)
        assertEquals("Content", note?.content)
    }

    @Test
    fun addNote() = runTest(testDispatcher) {
        val entity = Note(1L, "Title", "Content", createdAt = 1L, updatedAt = 1L)
        coEvery { dao.insert(any()) } returns 1000L

        val value = repository.addNote(entity)

        assertTrue(value == 1000L)
    }

    @Test
    fun updateNote() = runTest(testDispatcher) {
        val entity = Note(1L, "Title", "Content", createdAt = 1L, updatedAt = 1L)
        coEvery { dao.update(any()) } returns Unit

        val result = repository.updateNote(entity)

        assertTrue(result.isSuccess)
    }

    @Test
    fun deleteNote() = runTest(testDispatcher) {
        coEvery { dao.deleteById(1L) } returns Unit

        val result = repository.deleteNote(1L)

        assertTrue(result.isSuccess)
    }

}