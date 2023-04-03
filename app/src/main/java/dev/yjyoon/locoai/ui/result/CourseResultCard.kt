package dev.yjyoon.locoai.ui.result

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.yjyoon.locoai.R
import dev.yjyoon.locoai.ui.model.DateCourse
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CourseResultCard(
    course: DateCourse.Course,
    onRecreate: (DateCourse.Course, String) -> Unit,
    mode: CourseResultUiState.Mode
) {
    val uriHandler = LocalUriHandler.current
    val focusManager = LocalFocusManager.current

    var isEditing by remember { mutableStateOf(false) }
    var requirement by remember { mutableStateOf("") }

    ElevatedButton(
        onClick = { uriHandler.openUri(course.place.mapUrl) },
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text =
                    "${course.startTime.format(DateTimeFormatter.ofPattern(stringResource(id = R.string.time_fmt)))} ~ ${
                        course.endTime.format(
                            DateTimeFormatter.ofPattern(stringResource(id = R.string.time_fmt))
                        )
                    }",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 4.dp)
                )
            }
            AsyncImage(
                model = course.place.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = course.place.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 4.dp)
            )
            CourseResultCardRowTag(
                icon = Icons.Rounded.Label,
                text = course.place.category
            )
            CourseResultCardRowTag(
                icon = Icons.Rounded.LocationOn,
                text = course.place.address
            )
            CourseResultCardRowTag(
                icon = Icons.Rounded.Call,
                text = course.place.phone
            )
            CourseResultCardRowTag(
                icon = Icons.Rounded.Description,
                text = course.description
            )
            CourseResultCardRowTag(
                icon = Icons.Filled.ReceiptLong,
                text = course.budget.let {
                    if (it == 0) {
                        stringResource(id = R.string.free)
                    } else {
                        stringResource(
                            id = R.string.place_budget,
                            DecimalFormat("#,###").format(it)
                        )
                    }
                }
            )
            if (mode == CourseResultUiState.Mode.Result) {
                AnimatedContent(targetState = isEditing) {
                    if (it) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = requirement,
                                onValueChange = { requirement = it },
                                label = { Text(text = stringResource(id = R.string.requirement_textfield)) },
                                placeholder = { Text(text = stringResource(id = R.string.requirement_placeholder)) },
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16 / 9f)
                                    .padding(horizontal = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp)
                            ) {
                                Button(
                                    onClick = { isEditing = false },
                                    contentPadding = PaddingValues(
                                        vertical = 12.dp,
                                        horizontal = 16.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.onPrimary,
                                        contentColor = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.cancel),
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Button(
                                    onClick = {
                                        onRecreate(course, requirement)
                                        isEditing = false
                                        requirement = ""
                                    },
                                    contentPadding = PaddingValues(
                                        vertical = 12.dp,
                                        horizontal = 16.dp
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.recreate),
                                    )
                                }
                            }
                        }
                    } else {
                        Button(
                            onClick = { isEditing = true },
                            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(text = stringResource(id = R.string.change_course))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseResultCardRowTag(
    icon: ImageVector,
    text: String,
) {
    val color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = color,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
