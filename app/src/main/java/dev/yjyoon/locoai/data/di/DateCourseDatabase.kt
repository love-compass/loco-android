package dev.yjyoon.locoai.data.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import dev.yjyoon.locoai.data.model.CourseEntity
import dev.yjyoon.locoai.data.model.DateCourseEntity
import dev.yjyoon.locoai.data.source.DateCourseDao
import dev.yjyoon.locoai.ui.model.DateCourse
import java.time.LocalDate
import java.time.LocalDateTime

@Database(
    entities = [DateCourseEntity::class, CourseEntity::class],
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
    fun placeToJson(course: DateCourse.Course.Place): String = Gson().toJson(course)

    @TypeConverter
    fun jsonToPlace(json: String): DateCourse.Course.Place =
        Gson().fromJson(json, DateCourse.Course.Place::class.java)
}
