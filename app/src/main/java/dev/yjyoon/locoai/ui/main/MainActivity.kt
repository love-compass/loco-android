package dev.yjyoon.locoai.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.yjyoon.locoai.ui.input.CourseInputActivity
import dev.yjyoon.locoai.ui.theme.LocoaiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocoaiTheme {
                MainScreen(
                    navigateToInput = ::startCourseInputActivity
                )
            }
        }
    }

    private fun startCourseInputActivity() =
        CourseInputActivity.startActivity(this)
}
