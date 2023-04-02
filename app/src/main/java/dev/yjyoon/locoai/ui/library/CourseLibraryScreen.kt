package dev.yjyoon.locoai.ui.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CourseLibraryScreen(
    viewModel: CourseLibraryViewModel,
    onClose: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
}
