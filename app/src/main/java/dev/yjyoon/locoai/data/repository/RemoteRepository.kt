package dev.yjyoon.locoai.data.repository

import dev.yjyoon.locoai.data.model.DateCourseRequest
import dev.yjyoon.locoai.data.model.EditCourseRequest
import dev.yjyoon.locoai.data.source.ApiService
import dev.yjyoon.locoai.ui.model.DateCourse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RemoteRepository @Inject constructor(
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
        index: Int,
        dateCourse: DateCourse,
        require: String
    ) = runCatching {
        apiService.editCourse(
            EditCourseRequest(
                location = location,
                placeName = dateCourse.courses[index].place.name,
                otherPlaceNames = dateCourse.courses
                    .filterIndexed { idx, _ -> idx != index }
                    .map { it.place.name },
                requirement = require,
                startTime = dateCourse.courses[index].startTime.format(DateTimeFormatter.ISO_DATE_TIME),
                endTime = dateCourse.courses[index].endTime.format(DateTimeFormatter.ISO_DATE_TIME)
            )
        )
    }
}
