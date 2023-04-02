package dev.yjyoon.locoai.data.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import dev.yjyoon.locoai.data.model.DateCourseEntity
import dev.yjyoon.locoai.data.source.DateCourseDao
import dev.yjyoon.locoai.ui.model.DateCourse
import java.time.LocalDate
import java.time.LocalDateTime

@Database(
    entities = [DateCourseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class DateCourseDatabase : RoomDatabase() {
    abstract fun dateCourseDao(): DateCourseDao
}

object Converter {

    @TypeConverter
    fun stringToDateTime(string: String): LocalDateTime = LocalDateTime.parse(string)

    @TypeConverter
    fun dateTimeToString(dateTime: LocalDateTime) = dateTime.toString()

    @TypeConverter
    fun stringToDate(string: String): LocalDate = LocalDate.parse(string)

    @TypeConverter
    fun dateToString(dateTime: LocalDate): String = dateTime.toString()

    @TypeConverter
    fun courseToJson(course: DateCourse.Course): String = Gson().toJson(course)

    @TypeConverter
    fun jsonToCourse(json: String): DateCourse.Course =
        Gson().fromJson(json, DateCourse.Course::class.java)

    @TypeConverter
    fun courseListToJson(list: List<DateCourse.Course>): String = Gson().toJson(list)

    @TypeConverter
    fun jsonToCourseList(json: String): List<DateCourse.Course> =
        Gson().fromJson(json, Array<DateCourse.Course>::class.java).toList()
}
