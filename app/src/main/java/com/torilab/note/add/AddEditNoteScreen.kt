package com.torilab.note.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel,
    onBack: () -> Unit
) {

    val state = viewModel.noteState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.value.id != 0L) "Add Note" else "Edit Note") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.saveNote()
                onBack()
            }) {
                Text("✓")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.value.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = state.value.content,
                onValueChange = { viewModel.updateContent(it) },
                label = { Text("Content") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}