package dev.yjyoon.locoai.ui.library

import dev.yjyoon.locoai.ui.model.DateCourse

data class CourseLibraryUiState(
    val dateCourses: List<DateCourse> = emptyList(),
    val isLoading: Boolean = false
)
