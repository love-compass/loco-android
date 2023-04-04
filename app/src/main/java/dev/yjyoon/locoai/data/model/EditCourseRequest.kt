package dev.yjyoon.locoai.data.model

import com.google.gson.annotations.SerializedName

data class EditCourseRequest(
    @SerializedName("prior_activity_name")
    val placeName: String,
    @SerializedName("prior_places")
    val otherPlaceNames: List<String>,
    @SerializedName("place")
    val location: String,
    @SerializedName("user_request")
    val requirement: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String
)
