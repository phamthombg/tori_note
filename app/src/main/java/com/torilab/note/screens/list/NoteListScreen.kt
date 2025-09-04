package com.torilab.note.screens.list

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.torilab.note.R
import com.torilab.note.ui.utils.showMySnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onNavigateToAddEdit: (Long?) -> Unit
) {
    val viewModel: NoteListViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val notes = viewModel.notes.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
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

    // note list content
    NoteListContent(
        pagingItems = notes,
        snackBarHostState = snackBarHostState,
        onNoteClicked = { noteId ->
            onNavigateToAddEdit(noteId)
        },
        onDeleteButtonClicked = { noteId ->
            viewModel.deleteNote(noteId)
        },
        onAddClicked = {
            onNavigateToAddEdit(null)
        }
    )
}