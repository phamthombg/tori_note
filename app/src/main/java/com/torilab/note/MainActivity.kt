package com.torilab.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.torilab.note.nav.NotesNavGraph
import com.torilab.note.ui.theme.NoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteTheme {
                MaterialTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        val navController = rememberNavController()
                        NotesNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}