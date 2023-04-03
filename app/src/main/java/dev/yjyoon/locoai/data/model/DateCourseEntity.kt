package dev.yjyoon.locoai.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import dev.yjyoon.locoai.ui.model.DateCourse
import java.time.LocalDate
import java.time.LocalDateTime

data class DateCourseWrapper(
    @Embedded val dateCourse: DateCourseEntity,
    @Relation(
        parentColumn = "dateCourseId",
        entityColumn = "parentId"
    )
    val courses: List<CourseEntity>,
) {
    fun toModel() = DateCourse(
        location = dateCourse.location,
        totalBudget = dateCourse.totalBudget,
        courses = courses.map { it.toModel() },
        date = dateCourse.date
    )
}

@Entity(tableName = "date_course")
data class DateCourseEntity(
    @PrimaryKey(autoGenerate = true)
    var dateCourseId: Int? = null,
    val location: String,
    val totalBudget: Int,
    val date: LocalDate
)

@Entity(tableName = "course")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    var courseId: Int? = null,
    val parentId: Int,
    val place: DateCourse.Course.Place,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val budget: Int,
    val description: String
) {
    fun toModel() = DateCourse.Course(
        place = place,
        startTime = startTime,
        endTime = endTime,
        budget = budget,
        description = description
    )
}
