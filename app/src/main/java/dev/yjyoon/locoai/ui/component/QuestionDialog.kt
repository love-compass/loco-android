package dev.yjyoon.locoai.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.yjyoon.locoai.R

@Composable
fun QuestionDialog(
    title: String,
    question: String,
    onYes: () -> Unit,
    onNo: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        icon = {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = question)
            }
        },
        confirmButton = {
            TextButton(onClick = onYes) {
                Text(text = stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onNo) {
                Text(text = stringResource(id = R.string.no))
            }
        },
        modifier = modifier
    )
}
