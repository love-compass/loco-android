package dev.yjyoon.locoai.ui.model

import dev.yjyoon.locoai.data.model.CourseEntity
import dev.yjyoon.locoai.data.model.DateCourseEntity
import dev.yjyoon.locoai.data.model.DateCourseWrapper
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

data class DateCourse(
    val location: String,
    val totalBudget: Int,
    val courses: List<Course>,
    val date: LocalDate = courses[0].startTime.toLocalDate()
) : Serializable {

    fun toEntity() = DateCourseWrapper(
        dateCourse = DateCourseEntity(
            dateCourseId = this.hashCode(),
            location = location,
            totalBudget = totalBudget,
            date = date
        ),
        courses = courses.map { it.toEntity(this.hashCode()) },
    )

    data class Course(
        val place: Place,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val budget: Int,
        val description: String
    ) : Serializable {

        fun toEntity(hashcode: Int) = CourseEntity(
            parentId = hashcode,
            place = place,
            startTime = startTime,
            endTime = endTime,
            budget = budget,
            description = description
        )

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
