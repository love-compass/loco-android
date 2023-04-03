package dev.yjyoon.locoai.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.yjyoon.locoai.data.model.CourseEntity
import dev.yjyoon.locoai.data.model.DateCourseEntity
import dev.yjyoon.locoai.data.model.DateCourseWrapper

@Dao
interface DateCourseDao {

    @Insert
    suspend fun addDateCourse(dateCourse: DateCourseEntity)

    @Insert
    suspend fun addCourse(course: CourseEntity)

    @Delete
    suspend fun deleteDateCourse(dateCourse: DateCourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Transaction
    @Query("SELECT * FROM date_course")
    suspend fun getAllDateCourses(): List<DateCourseWrapper>
}
