package dev.yjyoon.locoai.ui.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.yjyoon.locoai.ui.theme.LocoaiTheme

@AndroidEntryPoint
class CourseInputActivity : ComponentActivity() {

    private val viewModel: CourseInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocoaiTheme {
                CourseInputScreen(
                    viewModel = viewModel,
                    onClose = ::finish
                )
            }
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, CourseInputActivity::class.java)
            context.startActivity(intent)
        }
    }
}
