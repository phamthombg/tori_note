package com.torilab.note.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.torilab.note.nav.Routes.NODE_ID_ARG
import com.torilab.note.screens.detail.AddEditNoteScreen
import com.torilab.note.screens.list.NoteListScreen

object Routes {
    const val NOTE_LIST = "note_list" // note list
    const val ADD_EDIT_NOTE = "add_edit_note" // add and edit note
    const val NODE_ID_ARG = "noteId" // argument for node id
}

@Composable
fun NotesNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.NOTE_LIST
    ) {
        composable(Routes.NOTE_LIST) {
            NoteListScreen(
                onNavigateToAddEdit = { noteId ->
                    navController.navigate("${Routes.ADD_EDIT_NOTE}?$NODE_ID_ARG=$noteId")
                }
            )
        }
        composable("${Routes.ADD_EDIT_NOTE}?$NODE_ID_ARG={noteId}") {
            AddEditNoteScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}