package com.torilab.note.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.torilab.note.R
import com.torilab.note.ui.dialog.ConfirmDialog
import com.torilab.note.ui.utils.rememberSafeClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel, onNavigateToAddEdit: (Long?) -> Unit
) {
    val notes = viewModel.notes.collectAsLazyPagingItems()
    // state of delete note dialog, default is Hide state
    var deleteNodeDialogState: DeleteNodeDialogState by remember {
        mutableStateOf(
            DeleteNodeDialogState.Hide
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.lbl_my_notes),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                    )
                })
        },
        floatingActionButton = {
            val safeClick = rememberSafeClick()
            FloatingActionButton(
                onClick = {
                    safeClick {
                        onNavigateToAddEdit(null)
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_note))
            }
        }) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(
                count = notes.itemCount,
                key = { index -> notes[index]?.id ?: index }) { index ->
                notes[index]?.let {
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart) {
                                deleteNodeDialogState = DeleteNodeDialogState.Show(it.id)
                                false
                            } else false
                        }
                    )

                    NoteItemContent(
                        note = it,
                        dismissState = dismissState,
                        onClick = { onNavigateToAddEdit(it.id) }
                    )
                }

                // add divider between items
                if (index < notes.itemCount - 1) {
                    HorizontalDivider(
                        thickness = 0.5f.dp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }

    // Handle show/hide delete node confirm dialog
    when (val state = deleteNodeDialogState) {
        is DeleteNodeDialogState.Show -> {
            ConfirmDialog(
                title = stringResource(R.string.delete_note),
                message = stringResource(R.string.confirm_delete_note_message),
                confirmText = stringResource(R.string.OK),
                cancelText = stringResource(R.string.Cancel),
                onConfirmed = {
                    viewModel.deleteNote(state.noteId)
                    deleteNodeDialogState = DeleteNodeDialogState.Hide
                },
                onCanceled = {
                    deleteNodeDialogState = DeleteNodeDialogState.Hide
                }
            )
        }

        DeleteNodeDialogState.Hide -> {
            // do nothing
        }
    }
}