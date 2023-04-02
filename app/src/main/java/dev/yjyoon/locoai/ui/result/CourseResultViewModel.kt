package dev.yjyoon.locoai.ui.result

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.locoai.data.repository.RemoteRepository
import dev.yjyoon.locoai.ui.model.DateCourse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CourseResultUiState(
            dateCourse = requireNotNull(savedStateHandle.get<DateCourse>(EXTRA_KEY_DATECOURSE))
        )
    )
    val uiState: StateFlow<CourseResultUiState> = _uiState.asStateFlow()

    fun changeCourse(location: String, course: DateCourse.Course, require: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000L)
            remoteRepository.editCourse(
                location = location,
                course = course,
                require = require
            )
                .onSuccess { newCourse ->
                    _uiState.update { state ->
                        state.copy(
                            dateCourse = state.dateCourse.copy(
                                courses = state.dateCourse.courses.map {
                                    if (it == course) newCourse.toModel() else it
                                }
                            )
                        )
                    }
                }
                .onFailure { Log.e("error", it.stackTraceToString()) }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        const val EXTRA_KEY_DATECOURSE = "EXTRA_KEY_DATECOURSE"
    }
}
