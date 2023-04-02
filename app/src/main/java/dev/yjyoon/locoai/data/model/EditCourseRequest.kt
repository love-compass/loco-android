package dev.yjyoon.locoai.data.model

import com.google.gson.annotations.SerializedName

data class EditCourseRequest(
    @SerializedName("prior_activity_name")
    val placeName: String,
    @SerializedName("place")
    val location: String,
    @SerializedName("question")
    val requirement: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String
)
