package dev.yjyoon.locoai.data.model

import com.google.gson.annotations.SerializedName
import dev.yjyoon.locoai.ui.model.DateCourse
import java.time.LocalDateTime

data class DateCourseResponse(
    @SerializedName("place")
    val location: String,
    @SerializedName("total_budget")
    val totalBudget: Int,
    @SerializedName("courses")
    val courses: List<Course>
) {

    fun toModel() = DateCourse(
        location = location,
        totalBudget = totalBudget,
        courses = courses.map { it.toModel() }
    )

    data class Course(
        @SerializedName("activity")
        val place: Place,
        @SerializedName("budget")
        val budget: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("start_time")
        val startTime: String,
        @SerializedName("end_time")
        val endTime: String
    ) {

        fun toModel() = DateCourse.Course(
            place = place.toModel(),
            budget = budget,
            description = description,
            startTime = LocalDateTime.parse(startTime),
            endTime = LocalDateTime.parse(endTime)
        )

        data class Place(
            @SerializedName("place_name")
            val name: String,
            @SerializedName("category_name")
            val category: String,
            @SerializedName("road_address_name")
            val address: String,
            @SerializedName("phone")
            val phone: String,
            @SerializedName("image_url")
            val imageUrl: String,
            @SerializedName("place_url")
            val mapUrl: String
        ) {

            fun toModel() = DateCourse.Course.Place(
                name, category, address, phone, imageUrl, mapUrl
            )
        }
    }
}
