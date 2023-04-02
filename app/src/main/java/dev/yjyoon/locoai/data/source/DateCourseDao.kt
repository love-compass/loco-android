package dev.yjyoon.locoai.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.yjyoon.locoai.data.model.DateCourseEntity

@Dao
interface DateCourseDao {

    @Insert
    suspend fun addDateCourse(dateCourse: DateCourseEntity)

    @Delete
    suspend fun deleteDateCourse(dateCourse: DateCourseEntity)

    @Query("SELECT * FROM date_course")
    suspend fun getAllDateCourses(): List<DateCourseEntity>
}
