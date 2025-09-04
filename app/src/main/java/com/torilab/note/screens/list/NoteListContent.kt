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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.torilab.domain.model.Note
import com.torilab.note.R
import com.torilab.note.ui.dialog.ConfirmDialog
import com.torilab.note.ui.utils.rememberSafeClick
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListContent(
    pagingItems: LazyPagingItems<Note>,
    snackBarHostState: SnackbarHostState,
    onNoteClicked: (Long) -> Unit,
    onDeleteButtonClicked: (Long) -> Unit,
    onAddClicked: () -> Unit
) {
    // state of delete note dialog, default is Hide state
    var deleteNodeDialogState: DeleteNoteDialogState by remember {
        mutableStateOf(
            DeleteNoteDialogState.Hide
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
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        floatingActionButton = {
            val safeClick = rememberSafeClick()
            FloatingActionButton(
                onClick = {
                    safeClick {
                        onAddClicked()
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
                count = pagingItems.itemCount,
                key = { index -> pagingItems[index]?.id ?: index }) { index ->
                pagingItems[index]?.let {
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart) {
                                deleteNodeDialogState = DeleteNoteDialogState.Show(it.id)
                                false
                            } else false
                        }
                    )

                    NoteItemContent(
                        note = it,
                        dismissState = dismissState,
                        onClick = { onNoteClicked(it.id) }
                    )
                }

                // add divider between items
                if (index < pagingItems.itemCount - 1) {
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
        is DeleteNoteDialogState.Show -> {
            ConfirmDialog(
                title = stringResource(R.string.delete_note),
                message = stringResource(R.string.confirm_delete_note_message),
                confirmText = stringResource(R.string.OK),
                cancelText = stringResource(R.string.Cancel),
                onConfirmed = {
                    onDeleteButtonClicked(state.noteId)
                    deleteNodeDialogState = DeleteNoteDialogState.Hide
                },
                onCanceled = {
                    deleteNodeDialogState = DeleteNoteDialogState.Hide
                }
            )
        }

        DeleteNoteDialogState.Hide -> {
            // do nothing
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListContentPreview() {
    val previewNodes = listOf(
        Note(id = 1, title = "First Note", content = "Hello World", updatedAt = 1L),
        Note(id = 2, title = "Second Note", content = "Helle ToriLab", updatedAt = 2L),
        Note(
            id = 3,
            title = "Introduce myself",
            content = "My name's ThomPV, I am 31 years old. Now I am an Android developer. Nice to meet you!",
            updatedAt = 3L
        )
    )

    val pagingFlow = remember { flowOf(PagingData.from(previewNodes)) }
    val lazyPagingItems = pagingFlow.collectAsLazyPagingItems()

    NoteListContent(
        pagingItems = lazyPagingItems,
        snackBarHostState = SnackbarHostState(),
        onNoteClicked = {},
        onDeleteButtonClicked = {},
        onAddClicked = {}
    )
}