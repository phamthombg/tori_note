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
class UpdateNoteUseCaseTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var updateNoteUseCase: UpdateNoteUseCase

    @Before
    fun setUp() {
        noteRepository = mockk()
        updateNoteUseCase = UpdateNoteUseCase(noteRepository)
    }

    @Test
    fun `when repository returns success, usecase should return success`() = runTest {
        val note = Note(id = 1L, title = "Title", content = "Content", updatedAt = 1L)
        coEvery { noteRepository.updateNote(note) } returns Result.success(Unit)

        val result = updateNoteUseCase(note)

        assertEquals(Result.success(Unit), result)
        coVerify(exactly = 1) { noteRepository.updateNote(note) }
    }

    @Test
    fun `when repository returns failure, usecase should return failure`() = runTest {
        val note = Note(id = 1L, title = "Title", content = "Content", updatedAt = 1L)
        val exception = RuntimeException("Update failed")
        coEvery { noteRepository.updateNote(note) } returns Result.failure(exception)

        val result = updateNoteUseCase(note)

        assertEquals(Result.failure<Unit>(exception), result)
        coVerify(exactly = 1) { noteRepository.updateNote(note) }
    }
}