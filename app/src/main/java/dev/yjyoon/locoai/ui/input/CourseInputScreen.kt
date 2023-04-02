package dev.yjyoon.locoai.ui.input

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.yjyoon.locoai.R
import dev.yjyoon.locoai.ui.component.QuestionDialog

@Composable
fun CourseInputScreen(
    viewModel: CourseInputViewModel,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    var step by remember { mutableStateOf(0) }
    var showCloseDialog by remember { mutableStateOf(false) }

    val question = CourseInput.courseInputQuestions[step]
    val maxStep = CourseInput.courseInputQuestions.size

    BackHandler {
        if (state is CourseInputUiState.Waiting) {
            if (step > 0) step--
            else {
                showCloseDialog = true
            }
        }
    }

    when (state) {
        CourseInputUiState.Waiting -> {
            Scaffold(
                topBar = {
                    CourseInputTopAppBar(
                        step = step,
                        maxStep = maxStep,
                        onClose = { showCloseDialog = true }
                    )
                },
                content = { innerPadding ->
                    InputContent(
                        state = state,
                        viewModel = viewModel,
                        question = question,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                },
                bottomBar = {
                    CourseInputBottomBar(
                        showPrevious = step > 0,
                        onPreviousClick = { step-- },
                        enabledNext = viewModel.isValidInput(step),
                        onNextClick = { step++ },
                        showDone = step + 1 == maxStep,
                        onDoneClick = { }
                    )
                }
            )

            if (showCloseDialog) {
                QuestionDialog(
                    title = stringResource(R.string.input_exit_dialog_title),
                    question = stringResource(R.string.input_exit_dialog_text),
                    onYes = {
                        showCloseDialog = false
                        onClose()
                    },
                    onNo = { showCloseDialog = false },
                    onDismissRequest = { showCloseDialog = false }
                )
            }
        }
        CourseInputUiState.Loading -> {

        }
        is CourseInputUiState.Success -> {

        }
        is CourseInputUiState.Failure -> {

        }
    }
}

@Composable
fun QuestionTextBox(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(20.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            textAlign = TextAlign.Start,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InputContent(
    state: CourseInputUiState,
    viewModel: CourseInputViewModel,
    question: CourseInput.Question,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.padding(horizontal = 18.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        QuestionTextBox(text = stringResource(id = question.questionText))
        Surface(
            modifier = Modifier.padding(top = 22.dp, bottom = 4.dp)
        ) {
            when (question.inputType) {
                CourseInput.Type.Place -> {
                    CoursePlaceInput(
                        places = CourseInputPlaces.list,
                        selected = viewModel.place,
                        onSelect = { viewModel.place = it }
                    )
                }
                CourseInput.Type.Time -> {
                    CourseTimeInput(
                        startTime = viewModel.startTime,
                        endTime = viewModel.endTime,
                        onChangeStart = { viewModel.startTime = it },
                        onChangeEnd = { viewModel.endTime = it }
                    )
                }
                CourseInput.Type.Budget -> {
                    CourseBudgetInput(
                        budget = viewModel.budget,
                        onChange = { viewModel.budget = it },
                        onAdd = viewModel::addBudget
                    )
                }
                CourseInput.Type.Require -> {
                    CourseRequireInput(
                        value = viewModel.require,
                        onChange = { viewModel.require = it }
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBarTitle(step: Int, maxStep: Int, modifier: Modifier = Modifier) {
    Text("${step + 1} / $maxStep", modifier = modifier.alpha(0.7f))
}

@Composable
fun StepProgressBar(step: Int, maxStep: Int) {
    val animatedProgress by animateFloatAsState(
        targetValue = (step + 1) / maxStep.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    LinearProgressIndicator(
        progress = animatedProgress,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun QuitButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(vertical = 12.dp)
    ) {
        Icon(
            Icons.Filled.Close,
            contentDescription = null,
            modifier = Modifier.alpha(0.7f)
        )
    }
}

@Composable
fun CourseInputTopAppBar(
    step: Int,
    maxStep: Int,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
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
            TopAppBarTitle(
                step = step,
                maxStep = maxStep,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            QuitButton(
                onClick = onClose
            )
        }
        StepProgressBar(step = step, maxStep = maxStep)
    }
}

@Composable
fun CourseInputBottomBar(
    showPrevious: Boolean,
    onPreviousClick: () -> Unit,
    enabledNext: Boolean,
    onNextClick: () -> Unit,
    showDone: Boolean,
    onDoneClick: () -> Unit
) {
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
            if (showPrevious) {
                Button(
                    onClick = onPreviousClick,
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.previous),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            Button(
                onClick = if (showDone) onDoneClick else onNextClick,
                enabled = enabledNext,
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 20.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (showDone) {
                        stringResource(id = R.string.done)
                    } else {
                        stringResource(id = R.string.next)
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
