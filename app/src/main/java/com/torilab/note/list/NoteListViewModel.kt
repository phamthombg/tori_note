package com.torilab.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.torilab.domain.model.Note
import com.torilab.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val useCases: NoteUseCases
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            useCases.getNotes().collect { _notes.value = it }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch { useCases.deleteNote(noteId) }
    }
}