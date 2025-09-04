package com.torilab.note.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.torilab.note.R
import com.torilab.note.screens.list.DeleteNoteDialogState
import com.torilab.note.ui.dialog.ConfirmDialog
import com.torilab.note.ui.utils.rememberSafeClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteContent(
    noteUiState: NoteUiState,
    snackBarHostState: SnackbarHostState,
    onDeleteNote: (Long) -> Unit,
    onSaveNoteClicked: () -> Unit,
    onUpdateTitle: (String) -> Unit,
    onUpdateContent: (String) -> Unit,
    onBack: () -> Unit
) {
    // state of delete note dialog, default is Hide state
    var deleteNodeDialogState: DeleteNoteDialogState by remember {
        mutableStateOf(
            DeleteNoteDialogState.Hide
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = if (noteUiState.id == 0L) {
                        stringResource(R.string.add_note)
                    } else {
                        stringResource(R.string.edit_note)
                    }
                )
            }, navigationIcon = {
                val safeClick = rememberSafeClick()
                IconButton(
                    onClick = {
                        safeClick {
                            onBack()
                        }
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back"
                    )
                }
            }, actions = {
                if (noteUiState.id != 0L) {
                    val safeClick = rememberSafeClick()
                    IconButton(onClick = {
                        safeClick {
                            deleteNodeDialogState = DeleteNoteDialogState.Show(noteUiState.id)
                        }
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_note)
                        )
                    }
                }
            })
        }, snackbarHost = {
            SnackbarHost(snackBarHostState)
        },

        floatingActionButton = {
            val safeClick = rememberSafeClick()
            FloatingActionButton(
                onClick = {
                    safeClick {
                        onSaveNoteClicked()
                    }
                }) {
                Icon(
                    Icons.Default.Check, contentDescription = stringResource(R.string.add_note)
                )
            }
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = noteUiState.title,
                onValueChange = {
                    onUpdateTitle(it)
                },
                label = { Text(stringResource(R.string.lbl_title)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = noteUiState.content,
                onValueChange = {
                    onUpdateContent(it)
                },
                label = { Text(stringResource(R.string.lbl_content)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Handle show/hide delete node confirm dialog
    when (val state = deleteNodeDialogState) {
        is DeleteNoteDialogState.Show -> {
            ConfirmDialog(
                title = stringResource(R.string.delete_note),
                message = stringResource(R.string.confirm_delete_note_message),
                confirmText = stringResource(R.string.OK),
                cancelText = stringResource(R.string.Cancel),
                onConfirmed = {
                    onDeleteNote(state.noteId)
                    deleteNodeDialogState = DeleteNoteDialogState.Hide
                },
                onCanceled = {
                    deleteNodeDialogState = DeleteNoteDialogState.Hide
                })
        }

        DeleteNoteDialogState.Hide -> {
            // do nothing
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditNoteContentPreview() {
    val noteUiState = NoteUiState(
        id = 1L, title = "Title is here", content = "Content is here"
    )

    AddEditNoteContent(
        noteUiState = noteUiState,
        snackBarHostState = SnackbarHostState(),
        onDeleteNote = {},
        onSaveNoteClicked = {},
        onUpdateTitle = {},
        onUpdateContent = {},
        onBack = {})
}