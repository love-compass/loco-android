package dev.yjyoon.locoai.ui.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yjyoon.locoai.ui.model.DateCourse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CourseResultUiState(
            dateCourse = requireNotNull(savedStateHandle.get<DateCourse>(EXTRA_KEY_DATECOURSE))
        )
    )
    val uiState: StateFlow<CourseResultUiState> = _uiState.asStateFlow()

    fun changeCourse(location: String, prevCourse: DateCourse.Course, require: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000L)
            val newCourse = DateCourse.Course(
                place = DateCourse.TEST_CHANGED_PLACE,
                startTime = prevCourse.startTime,
                endTime = prevCourse.endTime,
                budget = 100000,
                description = "수정된 데이트 코스 입니다 수정된 데이트 코스 입니다 수정된 데이트 코스 입니다 수정된 데이트 코스 입니다"
            )
            _uiState.update { state ->
                state.copy(
                    dateCourse = state.dateCourse.copy(
                        courses = state.dateCourse.courses.map { if (it == prevCourse) newCourse else it }
                    )
                )
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        const val EXTRA_KEY_DATECOURSE = "EXTRA_KEY_DATECOURSE"
    }
}