package com.torilab.note.screens.list

import androidx.paging.PagingData
import com.torilab.domain.usecase.NoteUseCases
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteListViewModelTest {
    private lateinit var noteUseCase: NoteUseCases
    private lateinit var viewModel: NoteListViewModel

    @Before
    fun setUp() {
        noteUseCase = mockk()
        every { noteUseCase.getNotes.invoke() } returns flowOf(PagingData.empty())
        viewModel = NoteListViewModel(noteUseCase)
    }

    @Test
    fun `deleteNote success when useCase succeeds`() = runTest {
        coEvery { noteUseCase.deleteNote.invoke(1L) } returns Result.success(Unit)
        viewModel.deleteNote(1L)
        val state = viewModel.deleteNoteState.first()
        assertEquals(DeleteNoteState.Success, state)
    }

    @Test
    fun `deleteNote failure when useCase fails`() = runTest {
        coEvery { noteUseCase.deleteNote.invoke(2L) } throws Exception("delete failed")
        viewModel.deleteNote(2L)
        val state = viewModel.deleteNoteState.first()
        assertEquals(DeleteNoteState.Error("delete failed"), state)
    }
}