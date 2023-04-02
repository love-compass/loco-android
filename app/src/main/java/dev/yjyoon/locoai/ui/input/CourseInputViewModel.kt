package dev.yjyoon.locoai.ui.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.locoai.data.repository.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CourseInputViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CourseInputUiState>(CourseInputUiState.Waiting)
    val uiState: StateFlow<CourseInputUiState> = _uiState.asStateFlow()

    var place by mutableStateOf<String?>(null)
    var startTime by mutableStateOf<LocalDateTime?>(null)
    var endTime by mutableStateOf<LocalDateTime?>(null)
    var budget by mutableStateOf<Int?>(null)
    var require by mutableStateOf("")

    fun addBudget(value: Int) {
        budget = (budget ?: 0) + value
    }

    fun isValidInput(step: Int): Boolean =
        when (step) {
            0 -> place != null
            1 -> startTime != null && endTime != null
            2 -> budget != null
            3 -> true
            else -> false
        }

    fun createCourse() {
        viewModelScope.launch {
            _uiState.value = CourseInputUiState.Loading
            remoteRepository.getDateCourse(
                place = place!!,
                budget = budget!!,
                require = require,
                startTime = startTime!!,
                endTime = endTime!!
            )
                .onSuccess {
                    _uiState.value = CourseInputUiState.Success(it.toModel())
                }
                .onFailure {
                    _uiState.value = CourseInputUiState.Failure(it)
                }
        }
    }
}
