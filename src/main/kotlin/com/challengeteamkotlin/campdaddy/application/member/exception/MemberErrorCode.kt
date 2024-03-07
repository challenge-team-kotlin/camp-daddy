package com.challengeteamkotlin.campdaddy.application.member.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String,
) : ErrorCode {
    ACCESS_DENIED(1000, HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    ;


}