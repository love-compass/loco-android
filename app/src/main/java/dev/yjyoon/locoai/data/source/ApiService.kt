package dev.yjyoon.locoai.data.source

import dev.yjyoon.locoai.data.model.DateCourseRequest
import dev.yjyoon.locoai.data.model.DateCourseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("course")
    suspend fun getDateCourse(@Body request: DateCourseRequest): DateCourseResponse
}
