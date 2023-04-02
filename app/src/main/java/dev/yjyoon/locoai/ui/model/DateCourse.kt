package dev.yjyoon.locoai.ui.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

data class DateCourse(
    val location: String,
    val totalBudget: Int,
    val courses: List<Course>,
    val date: LocalDate = courses[0].startTime.toLocalDate()
) : Serializable {
    data class Course(
        val place: Place,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val budget: Int,
        val description: String
    ) : Serializable {
        data class Place(
            val name: String,
            val category: String,
            val address: String,
            val phone: String,
            val imageUrl: String,
            val mapUrl: String,
        ) : Serializable
    }
}
