package com.challengeteamkotlin.campdaddy.application.member.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String,
) : ErrorCode {
    EMAIL_PASSWORD_MISMATCH(1000, HttpStatus.BAD_REQUEST, "Email 또는 Password를 다시 입력해 주세요."),
    EMAIL_NICKNAME_ALREADY_EXIST(1001, HttpStatus.BAD_REQUEST, "이미 존재합니다."),
    ACCESS_DENIED(1002, HttpStatus.UNAUTHORIZED, "권한이 없습니다.")
    ;


}