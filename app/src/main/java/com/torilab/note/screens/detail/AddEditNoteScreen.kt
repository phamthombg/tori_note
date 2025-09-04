package com.torilab.note.screens.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.torilab.note.R
import com.torilab.note.screens.list.DeleteNoteState
import com.torilab.note.ui.utils.showMySnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    onBack: () -> Unit
) {
    val viewModel: AddEditNoteViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }
    val state = viewModel.noteState.collectAsState()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.addUpdateNoteState.collect { state ->
            // handle state when save note
            when (state) {
                // Success state
                is AddUpdateNoteState.Success -> {
                    snackBarHostState.showMySnackbar(
                        context.getString(R.string.save_note_success_message),
                        scope
                    )
                }
                // Fail state
                is AddUpdateNoteState.Error -> {
                    snackBarHostState.showMySnackbar(
                        context.getString(R.string.save_note_failed_message),
                        scope
                    )
                }
            }
        }

        viewModel.deleteNoteState.collect { state ->
            // handle state when delete note
            when (state) {
                // Success state
                is DeleteNoteState.Success -> {
                    snackBarHostState.showMySnackbar(
                        context.getString(R.string.delete_note_success_message),
                        scope
                    )
                }
                // Fail state
                is DeleteNoteState.Error -> {
                    snackBarHostState.showMySnackbar(
                        context.getString(R.string.delete_note_failed_message),
                        scope
                    )
                }
            }
        }
    }

    AddEditNoteContent(
        noteUiState = state.value,
        snackBarHostState = snackBarHostState,
        onDeleteNote = { noteId ->
            viewModel.deleteNote(noteId)
            onBack()
        },
        onSaveNoteClicked = {
            if (viewModel.isValid()) {
                if (viewModel.isAddNewNote()) {
                    viewModel.addNote()
                } else {
                    viewModel.updateNote()
                }
                onBack()
            } else {
                snackBarHostState.showMySnackbar(
                    message = context.getString(R.string.input_wrong_message),
                    scope
                )
            }
        },
        onUpdateTitle = { title ->
            viewModel.updateTitle(title)
        },
        onUpdateContent = { content ->
            viewModel.updateContent(content)
        },
        onBack = {
            onBack()
        }
    )
}