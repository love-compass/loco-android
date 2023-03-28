package dev.yjyoon.locoai.ui.input

import java.time.LocalDateTime

data class CourseInputUiState(
    val place: String? = null,
    val startTime: LocalDateTime? = null,
    val endTime: LocalDateTime? = null,
    val budget: Int? = null,
    val require: String? = null
) {

    fun isValidInput(step: Int): Boolean =
        when (step) {
            0 -> place != null
            1 -> startTime != null && endTime != null
            2 -> budget != null
            3 -> true
            else -> false
        }

    companion object {
        val places = listOf(
            "강남/역삼/선릉",
            "강남구청",
            "개포/일원/수서",
            "건대/군자/구의",
            "금호/옥수",
            "명동/을지로/충무로",
            "방이",
            "북촌/삼청",
            "삼성/대치",
            "상수/합정/망원",
            "서울역/회현",
            "서초/방배",
            "서촌",
            "선릉/삼성",
            "성수/뚝섬",
            "신사/논현",
            "신촌/홍대/서교",
            "압구정/청담",
            "양재/도곡",
            "연남",
            "영동포/여의도",
            "용산/삼각지",
            "이태원/한남",
            "잠실/송파",
            "종로/광화문",
            "분당",
            "수원/광교",
            "판교"
        )
    }
}
