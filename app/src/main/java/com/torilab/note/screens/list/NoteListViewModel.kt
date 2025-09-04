package com.torilab.note.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.torilab.domain.model.Note
import com.torilab.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {

    private var _deleteNoteState = Channel<DeleteNoteState>(Channel.BUFFERED)
    val deleteNoteState = _deleteNoteState.receiveAsFlow()

    var notes: StateFlow<PagingData<Note>> =
        useCases.getNotes() // get notes from repository via NoteUseCases
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty()
            )

    /**
     * delete a note by ID's Note
     * @param noteId note id
     */
    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            try {
                useCases.deleteNote(noteId)
                _deleteNoteState.send(DeleteNoteState.Success)
            } catch (ex: Exception) {
                _deleteNoteState.send(DeleteNoteState.Error(ex.message ?: ""))
            }
        }
    }
}