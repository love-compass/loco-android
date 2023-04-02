package dev.yjyoon.locoai.ui.result

import dev.yjyoon.locoai.ui.model.DateCourse

data class CourseResultUiState(
    val dateCourse: DateCourse,
    val isLoading: Boolean = false
)
