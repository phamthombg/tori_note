package com.torilab.domain.usecase

import com.torilab.domain.model.Note
import com.torilab.domain.repository.NoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddNoteUseCaseTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var addNoteUseCase: AddNoteUseCase

    @Before
    fun setUp() {
        noteRepository = mockk()
        addNoteUseCase = AddNoteUseCase(noteRepository)
    }

    @Test
    fun `when repository returns success, use case should return success`() = runTest {
        val note = Note(id = 1, title = "Test", content = "Content", updatedAt = 1L)
        coEvery { noteRepository.addNote(note) } returns 1000L
        val result = addNoteUseCase(note)
        assertEquals(Result.success(1000L), result)
        coVerify(exactly = 1) { noteRepository.addNote(note) }
    }

    @Test
    fun `when repository returns failure, use case return failure`() = runTest {
        val note = Note(id = 1L, title = "Fail", content = "Content", updatedAt = 1L)
        val exception = RuntimeException("DB Exception")
        coEvery { noteRepository.addNote(note) } throws exception
        val result = addNoteUseCase(note)

        assertEquals(Result.failure<Unit>(exception), result)
        coVerify(exactly = 1) { noteRepository.addNote(note) }
    }
}