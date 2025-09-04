package com.torilab.domain.usecase

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
class DeleteNoteUseCaseTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @Before
    fun setUp() {
        noteRepository = mockk()
        deleteNoteUseCase = DeleteNoteUseCase(noteRepository)
    }

    @Test
    fun `when repository returns success, use case should return success`() = runTest {
        val noteId = 1L
        coEvery { noteRepository.deleteNote(noteId) } returns Result.success(Unit)

        val result = deleteNoteUseCase(noteId)

        assertEquals(Result.success(Unit), result)
        coVerify(exactly = 1) { noteRepository.deleteNote(noteId) }
    }

    @Test
    fun `when repository returns failure, use case should return failure`() = runTest {
        val noteId = 2L
        val exception = RuntimeException("Delete failed")
        coEvery { noteRepository.deleteNote(noteId) } returns Result.failure(exception)

        val result = deleteNoteUseCase(noteId)

        assertEquals(Result.failure<Unit>(exception), result)
        coVerify(exactly = 1) { noteRepository.deleteNote(noteId) }
    }
}