package dev.yjyoon.locoai.ui.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CourseInputViewModel @Inject constructor() : ViewModel() {

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
}
