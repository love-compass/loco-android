package dev.yjyoon.locoai.ui.input

import dev.yjyoon.locoai.ui.model.DateCourse

sealed interface CourseInputUiState {
    data class Success(val course: DateCourse) : CourseInputUiState
    object Loading : CourseInputUiState
    object Waiting : CourseInputUiState
    data class Failure(val exception: Throwable?) : CourseInputUiState
}
