package com.torilab.note.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.torilab.domain.model.Note
import com.torilab.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    var notes: StateFlow<PagingData<Note>> =
        useCases.getNotes()
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty()
            )


    /**
     * delete a note by ID's Note
     */
    fun deleteNote(noteId: Long) {
        viewModelScope.launch { useCases.deleteNote(noteId) }
    }
}