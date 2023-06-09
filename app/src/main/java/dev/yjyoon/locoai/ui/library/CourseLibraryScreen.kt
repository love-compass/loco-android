package dev.yjyoon.locoai.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yjyoon.locoai.R
import dev.yjyoon.locoai.ui.component.QuestionDialog
import dev.yjyoon.locoai.ui.input.QuitButton
import dev.yjyoon.locoai.ui.model.DateCourse

@Composable
fun CourseLibraryScreen(
    viewModel: CourseLibraryViewModel,
    onClickItem: (DateCourse) -> Unit,
    onClose: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    var selectedDateCourse by remember { mutableStateOf<DateCourse?>(null) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    enabled = false,
                    onClick = { },
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = null,
                        modifier = Modifier.alpha(0f)
                    )
                }
                Text(
                    text = stringResource(id = R.string.course_view),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                QuitButton(onClick = onClose)
            }
            if (state.dateCourses.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = listState
                ) {
                    items(items = state.dateCourses) {
                        CourseLibraryCard(
                            dateCourse = it,
                            onClick = { onClickItem(it) },
                            onDelete = { selectedDateCourse = it }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.67f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = R.string.empty_saved),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.67f)
                        )
                    }
                }
            }
        }
    }
    if (selectedDateCourse != null) {
        QuestionDialog(
            title = stringResource(R.string.delete_course_dialog),
            question = stringResource(R.string.delete_course_dialog_text),
            onYes = {
                viewModel.deleteDateCourse(selectedDateCourse!!)
                selectedDateCourse = null
            },
            onNo = { selectedDateCourse = null },
            onDismissRequest = { selectedDateCourse = null }
        )
    }
}
