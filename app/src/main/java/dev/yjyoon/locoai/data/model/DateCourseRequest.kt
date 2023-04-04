package dev.yjyoon.locoai.data.model

import com.google.gson.annotations.SerializedName

data class DateCourseRequest(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("place")
    val place: String,
    @SerializedName("user_request")
    val requirement: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String
)
