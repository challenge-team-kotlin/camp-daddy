package com.challengeteamkotlin.campdaddy.application.reservation.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class ReservationErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String?
) : ErrorCode {
    RESERVATION_ENTITY_NOT_FOUND(4000, HttpStatus.BAD_REQUEST, "예약 데이터를 찾을 수 없습니다."),
    START_DATE_LESS_THAN_END_DATE(4001, HttpStatus.BAD_REQUEST, "시작 날짜는 종료 날짜보다 뒤 일 수 없습니다."),
    ALREADY_RESERVED_DATE(4002, HttpStatus.BAD_REQUEST, "이미 예약이 된 날짜입니다.")


    ;

    constructor(id: Long, status: HttpStatus) : this(id, status, null)
}
