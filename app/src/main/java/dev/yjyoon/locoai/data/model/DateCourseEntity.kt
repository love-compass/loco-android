package dev.yjyoon.locoai.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.yjyoon.locoai.ui.model.DateCourse
import java.time.LocalDate

@Entity(tableName = "date_course")
data class DateCourseEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val location: String,
    val totalBudget: Int,
    val courses: List<DateCourse.Course>,
    val date: LocalDate = courses[0].startTime.toLocalDate()
) {
    
    fun toModel() = DateCourse(
        location = location,
        totalBudget = totalBudget,
        courses = courses,
        date = date
    )
}
