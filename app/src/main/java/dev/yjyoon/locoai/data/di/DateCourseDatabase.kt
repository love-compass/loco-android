package dev.yjyoon.locoai.data.di

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.yjyoon.locoai.data.model.DateCourseEntity
import dev.yjyoon.locoai.data.source.DateCourseDao

@Database(
    entities = [DateCourseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DateCourseDatabase : RoomDatabase() {
    abstract fun dateCourseDao(): DateCourseDao
}
