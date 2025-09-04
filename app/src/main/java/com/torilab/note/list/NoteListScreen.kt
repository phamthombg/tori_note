package com.torilab.note.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.torilab.domain.model.Note

@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel,
    onNavigateToAddEdit: (Long?) -> Unit
) {
    val state = viewModel.notes.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToAddEdit(null) }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.value.size) { index ->
                val note = state.value[index]
                NoteItem(
                    note = note,
                    onClick = { onNavigateToAddEdit(note.id) },
                    onDelete = { viewModel.deleteNote(note.id) }
                )
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(note.title)
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}