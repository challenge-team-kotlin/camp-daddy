package com.challengeteamkotlin.campdaddy.common.exception.code

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String?
) : ErrorCode {
    VALIDATION_FAILED(9000, HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(9001, HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    ID_NOT_FOUND(9002, HttpStatus.BAD_REQUEST, "조회 된 회원이 없습니다.")
    ;
    constructor(id: Long, status: HttpStatus) : this(id, status, null)
}
