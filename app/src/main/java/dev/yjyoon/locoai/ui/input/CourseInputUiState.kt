package dev.yjyoon.locoai.ui.input

sealed interface CourseInputUiState {
    data class Success(val course: String) : CourseInputUiState
    object Loading : CourseInputUiState
    object Waiting : CourseInputUiState
    data class Failure(val exception: Throwable?) : CourseInputUiState
}
