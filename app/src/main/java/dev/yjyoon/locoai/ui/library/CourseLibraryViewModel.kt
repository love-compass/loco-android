package dev.yjyoon.locoai.ui.library

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.locoai.data.repository.LocalRepository
import dev.yjyoon.locoai.ui.model.DateCourse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseLibraryViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CourseLibraryUiState())
    val uiState: StateFlow<CourseLibraryUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            localRepository.getAllDateCourses()
                .onSuccess { result ->
                    _uiState.update { it.copy(dateCourses = result) }
                }
                .onFailure {
                    Log.e("error", it.stackTraceToString())
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun deleteDateCourse(dateCourse: DateCourse) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            localRepository.deleteDateCourse(dateCourse).onSuccess { refresh() }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}
