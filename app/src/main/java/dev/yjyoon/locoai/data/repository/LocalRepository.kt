package dev.yjyoon.locoai.data.repository

import dev.yjyoon.locoai.data.source.DateCourseDao
import dev.yjyoon.locoai.ui.model.DateCourse
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val dateCourseDao: DateCourseDao
) {

    suspend fun addDateCourse(dateCourse: DateCourse): Result<Unit> = runCatching {
        dateCourseDao.addDateCourse(dateCourse.toEntity())
    }

    suspend fun deleteDateCourse(dateCourse: DateCourse): Result<Unit> = runCatching {
        dateCourseDao.deleteDateCourse(dateCourse.toEntity())
    }

    suspend fun getAllDateCourses(): Result<List<DateCourse>> = runCatching {
        dateCourseDao.getAllDateCourses().map { it.toModel() }
    }
}
