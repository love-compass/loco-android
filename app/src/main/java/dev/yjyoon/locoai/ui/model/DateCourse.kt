package dev.yjyoon.locoai.ui.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

data class DateCourse(
    val location: String,
    val totalBudget: Int,
    val courses: List<Course>,
    val date: LocalDate = courses[0].startTime.toLocalDate()
) : Serializable {
    data class Course(
        val place: Place,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val budget: Int,
        val description: String
    ) : Serializable {
        data class Place(
            val name: String,
            val category: String,
            val address: String,
            val phone: String,
            val imageUrl: String,
            val mapUrl: String,
        ) : Serializable
    }

    companion object {
        val TEST_PLACE = Course.Place(
            name = "데이트 장소 1",
            category = "음식점 > 카페 > 커피 전문점",
            address = "강남구 테헤란로 549",
            phone = "010-1234-1234",
            imageUrl = "https://avatars.githubusercontent.com/u/72238126?v=4",
            mapUrl = "https://github.com"
        )
        val TEST_CHANGED_PLACE = Course.Place(
            name = "바뀐 데이트 장소 1",
            category = "음식점 > 카페 > 커피 전문점",
            address = "강남구 테헤란로 549",
            phone = "010-1234-1234",
            imageUrl = "https://avatars.githubusercontent.com/u/72238126?v=4",
            mapUrl = "https://github.com"
        )
        val TEST_COURSE = DateCourse(
            location = "강남/역삼/선릉",
            totalBudget = 150000,
            courses = listOf(
                Course(
                    place = TEST_PLACE,
                    startTime = LocalDateTime.now().plusHours(1),
                    endTime = LocalDateTime.now().plusHours(2).plusMinutes(30),
                    budget = 50000,
                    description = "데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다"
                ),
                Course(
                    place = TEST_PLACE,
                    startTime = LocalDateTime.now().plusHours(3),
                    endTime = LocalDateTime.now().plusHours(4).plusMinutes(30),
                    budget = 50000,
                    description = "데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다"
                ),
                Course(
                    place = TEST_PLACE,
                    startTime = LocalDateTime.now().plusHours(5),
                    endTime = LocalDateTime.now().plusHours(6).plusMinutes(30),
                    budget = 50000,
                    description = "데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다"
                ),
                Course(
                    place = TEST_PLACE,
                    startTime = LocalDateTime.now().plusHours(7),
                    endTime = LocalDateTime.now().plusHours(8).plusMinutes(30),
                    budget = 50000,
                    description = "데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다 데이트 코스 입니다"
                )
            )
        )
    }
}
