package dev.yjyoon.locoai.ui.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.yjyoon.locoai.ui.model.DateCourse
import dev.yjyoon.locoai.ui.theme.LocoaiTheme

@AndroidEntryPoint
class CourseResultActivity : ComponentActivity() {

    private val viewModel: CourseResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocoaiTheme {
                CourseResultScreen(
                    viewModel = viewModel,
                    onClose = ::finish
                )
            }
        }
    }

    companion object {
        fun startActivity(
            context: Context,
            dateCourse: DateCourse,
            mode: CourseResultUiState.Mode = CourseResultUiState.Mode.Result
        ) {
            val intent = Intent(context, CourseResultActivity::class.java)
                .putExtra(CourseResultViewModel.EXTRA_KEY_DATECOURSE, dateCourse)
                .putExtra(CourseResultViewModel.EXTRA_KEY_MODE, mode)
            
            context.startActivity(intent)
        }
    }
}
