package com.torilab.domain.usecase

import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNoteByIdUseCaseTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var getNotesUseCase: GetNoteByIdUseCase

    @Before
    fun setUp() {
        noteRepository = mockk()
        getNotesUseCase = GetNoteByIdUseCase(noteRepository)
    }

    @Test
    fun `when repository emits a note, use case should emit the same note`() = runTest {
        val noteId = 1L
        val note = Note(id = noteId, title = "Title", content = "Content", updatedAt = 1L)
        coEvery { noteRepository.getNoteById(noteId) } returns note

        val result = getNotesUseCase(noteId)
        assertEquals(note, result)
    }

    @Test
    fun `when repository emits null, use case should emit null`() = runTest {
        val noteId = 1L
        coEvery { noteRepository.getNoteById(noteId) } returns null

        val result = getNotesUseCase(noteId)
        assertEquals(null, result)
    }
}