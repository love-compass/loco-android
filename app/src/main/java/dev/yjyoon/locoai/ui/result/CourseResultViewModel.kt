package dev.yjyoon.locoai.ui.result

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.locoai.data.repository.LocalRepository
import dev.yjyoon.locoai.data.repository.RemoteRepository
import dev.yjyoon.locoai.ui.model.DateCourse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CourseResultUiState(
            dateCourse = requireNotNull(savedStateHandle.get<DateCourse>(EXTRA_KEY_DATECOURSE)),
            mode = requireNotNull(savedStateHandle.get<CourseResultUiState.Mode>(EXTRA_KEY_MODE))
        )
    )
    val uiState: StateFlow<CourseResultUiState> = _uiState.asStateFlow()

    fun changeCourse(
        location: String,
        index: Int,
        dateCourse: DateCourse,
        require: String
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            remoteRepository.editCourse(
                location = location,
                index = index,
                dateCourse = dateCourse,
                require = require
            )
                .onSuccess { newCourse ->
                    _uiState.update { state ->
                        state.copy(
                            dateCourse = state.dateCourse.copy(
                                courses = state.dateCourse.courses.map {
                                    if (it == dateCourse.courses[index]) newCourse.toModel()
                                    else it
                                },
                                totalBudget = state.dateCourse.totalBudget +
                                        newCourse.budget - dateCourse.courses[index].budget
                            )
                        )
                    }
                }
                .onFailure { Log.e("error", it.stackTraceToString()) }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun addDateCourse(dateCourse: DateCourse) {
        viewModelScope.launch {
            localRepository.addDateCourse(dateCourse)
        }
    }

    companion object {
        const val EXTRA_KEY_DATECOURSE = "EXTRA_KEY_DATECOURSE"
        const val EXTRA_KEY_MODE = "EXTRA_KEY_MODE"
    }
}
