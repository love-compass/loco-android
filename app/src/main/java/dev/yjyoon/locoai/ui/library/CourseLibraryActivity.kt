package dev.yjyoon.locoai.ui.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.yjyoon.locoai.ui.model.DateCourse
import dev.yjyoon.locoai.ui.result.CourseResultActivity
import dev.yjyoon.locoai.ui.result.CourseResultUiState
import dev.yjyoon.locoai.ui.theme.LocoaiTheme

@AndroidEntryPoint
class CourseLibraryActivity : ComponentActivity() {

    private val viewModel: CourseLibraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocoaiTheme {
                CourseLibraryScreen(
                    viewModel = viewModel,
                    onClickItem = ::startResultActivity,
                    onClose = ::finish
                )
            }
        }
    }


    private fun startResultActivity(dateCourse: DateCourse) =
        CourseResultActivity.startActivity(this, dateCourse, CourseResultUiState.Mode.Library)

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CourseLibraryActivity::class.java)
            context.startActivity(intent)
        }
    }
}
