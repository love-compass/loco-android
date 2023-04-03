package dev.yjyoon.locoai.ui.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.yjyoon.locoai.R
import dev.yjyoon.locoai.ui.model.DateCourse
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

@Composable
fun CourseLibraryCard(
    dateCourse: DateCourse,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = dateCourse.courses[0].place.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(screenWidth / 3)
                    .aspectRatio(4 / 3f)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dateCourse.location,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                CourseLibraryCardRowTag(
                    icon = Icons.Rounded.CalendarToday,
                    text = dateCourse.date.format(
                        DateTimeFormatter.ofPattern(stringResource(id = R.string.date_with_year_fmt))
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                CourseLibraryCardRowTag(
                    icon = Icons.Rounded.NearMe,
                    text = stringResource(
                        id = R.string.visited_place_count,
                        dateCourse.courses.size
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CourseLibraryCardRowTag(
                        icon = Icons.Filled.ReceiptLong,
                        text = dateCourse.totalBudget.let {
                            if (it == 0) {
                                stringResource(id = R.string.free)
                            } else {
                                stringResource(
                                    id = R.string.total_budget,
                                    DecimalFormat("#,###").format(it)
                                )
                            }
                        }
                    )
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onDelete() }
                    )
                }
            }
        }
    }
}

@Composable
fun CourseLibraryCardRowTag(
    icon: ImageVector,
    text: String,
) {
    val color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = color,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}
