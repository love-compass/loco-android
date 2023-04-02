package dev.yjyoon.locoai.data.repository

import dev.yjyoon.locoai.data.model.DateCourseRequest
import dev.yjyoon.locoai.data.model.EditCourseRequest
import dev.yjyoon.locoai.data.source.ApiService
import dev.yjyoon.locoai.ui.model.DateCourse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getDateCourse(
        place: String,
        budget: Int,
        require: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime
    ) = runCatching {
        apiService.getDateCourse(
            DateCourseRequest(
                place = place,
                budget = budget,
                requirement = require,
                startTime = startTime.format(DateTimeFormatter.ISO_DATE_TIME),
                endTime = endTime.format(DateTimeFormatter.ISO_DATE_TIME)
            )
        )
    }

    suspend fun editCourse(
        location: String,
        course: DateCourse.Course,
        require: String
    ) = runCatching {
        apiService.editCourse(
            EditCourseRequest(
                location = location,
                placeName = course.place.name,
                requirement = require,
                startTime = course.startTime.format(DateTimeFormatter.ISO_DATE_TIME),
                endTime = course.endTime.format(DateTimeFormatter.ISO_DATE_TIME)
            )
        )
    }
}