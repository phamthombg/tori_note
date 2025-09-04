package com.torilab.note.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.torilab.domain.model.Note
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

                    NoteItem(
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

@Composable
fun NoteItem(
    note: Note,
    dismissState: SwipeToDismissBoxState,
    onClick: () -> Unit
) {
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // only swipe right to left
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_note),
                            tint = Color.White
                        )
                    }
                }
            }
        },
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
                onClick = onClick
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}