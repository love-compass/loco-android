package dev.yjyoon.locoai.ui.input

import androidx.annotation.StringRes
import dev.yjyoon.locoai.R

sealed class CourseInput {
    data class Question(
        val number: Int,
        @StringRes val questionText: Int,
        val inputType: Type
    )

    enum class Type { Place, Time, Budget, Require }

    companion object {
        val courseInputQuestions = listOf(
            Question(1, R.string.input_place, Type.Place),
            Question(2, R.string.input_time, Type.Time),
            Question(3, R.string.input_budget, Type.Budget),
            Question(4, R.string.input_require, Type.Require),
        )
    }
}
