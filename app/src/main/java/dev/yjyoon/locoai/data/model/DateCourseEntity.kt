package dev.yjyoon.locoai.data.model

import androidx.room.Entity
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "date_course")
data class DateCourseEntity(
    val location: String,
    val totalBudget: Int,
    val courses: List<CourseEntity>,
    val date: LocalDate = courses[0].startTime.toLocalDate()
) {
    data class CourseEntity(
        val place: PlaceEntity,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val budget: Int,
        val description: String
    ) {
        data class PlaceEntity(
            val name: String,
            val category: String,
            val address: String,
            val phone: String,
            val imageUrl: String,
            val mapUrl: String,
        )
    }
}
