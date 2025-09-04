package com.torilab.note.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.torilab.note.R
import com.torilab.note.ui.utils.rememberSafeClick
import com.torilab.note.ui.utils.showMySnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel, onBack: () -> Unit
) {
    val state = viewModel.noteState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = if (state.value.id == 0L) {
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
                        if (viewModel.isValid()) {
                            viewModel.saveNote()
                            onBack()
                        } else {
                            snackBarHostState.showMySnackbar(
                                message = context.getString(R.string.input_wrong_message),
                                scope
                            )
                        }
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
                value = state.value.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = { Text(stringResource(R.string.lbl_title)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = state.value.content,
                onValueChange = { viewModel.updateContent(it) },
                label = { Text(stringResource(R.string.lbl_content)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}