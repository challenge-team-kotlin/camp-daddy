package com.challengeteamkotlin.campdaddy.domain.model.reservation

enum class ReservationStatus(
    val value: String
) {
    REQ("요청"), CONFIRM("요청 승인"), RENT("대여"), END("대여 종료"), CANCELED("취소")

    ;
}