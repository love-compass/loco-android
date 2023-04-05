package dev.yjyoon.locoai.ui.result

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yjyoon.locoai.R
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

@Composable
fun CourseResultScreen(
    viewModel: CourseResultViewModel,
    onClose: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val listState = rememberLazyListState()
    var isSaved by remember { mutableStateOf(false) }

    BackHandler {
        if (state.isLoading.not()) onClose()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                Surface(
                    tonalElevation = 2.dp,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Button(
                            onClick = onClose,
                            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when (state.mode) {
                                    CourseResultUiState.Mode.Result -> MaterialTheme.colorScheme.onPrimary
                                    CourseResultUiState.Mode.Library -> MaterialTheme.colorScheme.primary
                                },
                                contentColor = when (state.mode) {
                                    CourseResultUiState.Mode.Result -> MaterialTheme.colorScheme.primary
                                    CourseResultUiState.Mode.Library -> MaterialTheme.colorScheme.onPrimary
                                }
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.confirm),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (state.mode == CourseResultUiState.Mode.Result) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Button(
                                onClick = {
                                    viewModel.addDateCourse(state.dateCourse)
                                    isSaved = true
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.course_saved),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                enabled = isSaved.not(),
                                contentPadding = PaddingValues(
                                    vertical = 16.dp,
                                    horizontal = 20.dp
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.save),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(108.dp))
                }
                item {
                    Text(
                        text = state.dateCourse.location,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                item {
                    Text(
                        text = stringResource(
                            id = R.string.course_view_sub,
                            state.dateCourse.date.format(
                                DateTimeFormatter.ofPattern(stringResource(id = R.string.date_fmt))
                            )
                        ),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                items(state.dateCourse.courses.size) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CourseResultCard(
                            course = state.dateCourse.courses[it],
                            onRecreate = { require ->
                                viewModel.changeCourse(
                                    location = state.dateCourse.location,
                                    index = it,
                                    dateCourse = state.dateCourse,
                                    require = require
                                )
                                isSaved = false
                            },
                            mode = state.mode
                        )
                        Divider(modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
                item {
                    Text(
                        text = stringResource(
                            id = R.string.total_budget,
                            DecimalFormat("#,###").format(state.dateCourse.totalBudget)
                        ),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.67f)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp),
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 36.dp)
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = stringResource(id = R.string.course_creating))
                    }
                }
            }
        }
    }
}
