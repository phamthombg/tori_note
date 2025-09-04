package com.torilab.note.ui.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * show snackbar
 */
fun SnackbarHostState.showMySnackbar(
    message: String,
    coroutineScope: CoroutineScope,
) {
    coroutineScope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(message)
    }
}

@Composable
fun rememberSafeClick(
    delayMillis: Long = 500L
): (onClick: () -> Unit) -> Unit {
    val scope = rememberCoroutineScope()
    var enabled by remember { mutableStateOf(true) }

    return { onClick ->
        if (enabled) {
            enabled = false
            onClick()
            scope.launch {
                delay(delayMillis)
                enabled = true
            }
        }
    }
}