package dev.yjyoon.locoai.ui.input

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CourseInputViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CourseInputUiState())
    val uiState: StateFlow<CourseInputUiState> = _uiState.asStateFlow()

    fun setPlace(place: String) = _uiState.update { it.copy(place = place) }

    fun setStartTime(startTime: LocalDateTime) = _uiState.update { it.copy(startTime = startTime) }

    fun setEndTime(endTime: LocalDateTime) = _uiState.update { it.copy(endTime = endTime) }

    fun setBudget(budget: Int?) = _uiState.update { it.copy(budget = budget) }

    fun addBudget(value: Int) {
        val budget = (_uiState.value.budget ?: 0) + value
        _uiState.update { it.copy(budget = budget) }
    }

    fun setRequire(require: String) = _uiState.update { it.copy(require = require) }
}
