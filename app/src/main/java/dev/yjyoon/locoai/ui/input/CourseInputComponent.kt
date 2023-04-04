package dev.yjyoon.locoai.ui.input

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.yjyoon.locoai.R
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun CoursePlaceInput(
    places: List<String>,
    selected: String?,
    onSelect: (String) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
        items(places.size) {
            Button(
                onClick = { onSelect(places[it]) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = if (places[it] == selected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    containerColor = if (places[it] == selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                    }
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(text = places[it])
            }
        }
    }
}

@Composable
fun CourseTimeInput(
    startTime: LocalDateTime?,
    endTime: LocalDateTime?,
    onChangeStart: (LocalDateTime) -> Unit,
    onChangeEnd: (LocalDateTime) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val startTimePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute -> onChangeStart(LocalDateTime.now().withHour(hour).withMinute(minute)) },
        calendar[Calendar.HOUR_OF_DAY],
        calendar[Calendar.MINUTE],
        false
    )

    val endTimePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute -> onChangeEnd(LocalDateTime.now().withHour(hour).withMinute(minute)) },
        calendar[Calendar.HOUR_OF_DAY],
        calendar[Calendar.MINUTE],
        false
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.start_time),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { startTimePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = if (startTime != null) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    containerColor = if (startTime != null) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                    }
                ),
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = startTime?.format(
                        DateTimeFormatter.ofPattern(
                            stringResource(id = R.string.time_fmt)
                        )
                    ) ?: stringResource(id = R.string.input_time_button),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.end_time),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { endTimePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = if (endTime != null) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    containerColor = if (endTime != null) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                    }
                ),
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = endTime?.format(
                        DateTimeFormatter.ofPattern(
                            stringResource(id = R.string.time_fmt)
                        )
                    ) ?: stringResource(id = R.string.input_time_button),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
fun CourseBudgetInput(
    budget: Int?,
    onChange: (Int?) -> Unit,
    onAdd: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = budget?.let {
                DecimalFormat("#,###").format(it)
            } ?: "",
            onValueChange = { value ->
                if (value.length <= 10)
                    onChange(value.filter { it.isDigit() }.toIntOrNull())
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge,
            placeholder = {
                Text(
                    text = "100,000",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.alpha(0.5f)
                )
            },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            trailingIcon = { Text(text = "원") },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            listOf(10000, 50000, 100000).forEach {
                AddBudgetChip(value = it, onClick = { onAdd(it) })
            }
        }
    }
}

@Composable
fun AddBudgetChip(
    value: Int,
    onClick: (Int) -> Unit
) {
    ElevatedButton(
        onClick = { onClick(value) },
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(text = DecimalFormat("#,###").format(value))
    }
}

@Composable
fun CourseRequireInput(
    value: String?,
    onChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = value ?: "",
            onValueChange = onChange,
            label = { Text(text = stringResource(id = R.string.requirement_textfield)) },
            placeholder = { Text(text = stringResource(id = R.string.requirement_placeholder)) },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }
}

@Composable
fun CourseInputLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .align(Alignment.Center)
                .shadow(elevation = 12.dp),
            color = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = stringResource(id = R.string.course_creating),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
