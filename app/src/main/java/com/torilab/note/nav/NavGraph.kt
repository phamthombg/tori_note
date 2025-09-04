package com.torilab.note.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.torilab.note.add.AddEditNoteScreen
import com.torilab.note.add.AddEditNoteViewModel
import com.torilab.note.list.NoteListScreen
import com.torilab.note.list.NoteListViewModel

object Routes {
    const val NOTE_LIST = "note_list" // note list
    const val ADD_EDIT_NOTE = "add_edit_note" // add and edit note
}

@Composable
fun NotesNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.NOTE_LIST
    ) {
        composable(Routes.NOTE_LIST) {
            val viewModel: NoteListViewModel = hiltViewModel()
            NoteListScreen(
                viewModel = viewModel,
                onNavigateToAddEdit = { noteId ->
                    navController.navigate("${Routes.ADD_EDIT_NOTE}?noteId=$noteId")
                }
            )
        }
        composable("${Routes.ADD_EDIT_NOTE}?noteId={noteId}") {
            val viewModel: AddEditNoteViewModel = hiltViewModel()
            AddEditNoteScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}