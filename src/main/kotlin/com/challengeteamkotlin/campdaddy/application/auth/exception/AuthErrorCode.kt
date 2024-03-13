package com.challengeteamkotlin.campdaddy.application.auth.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String,
) : ErrorCode {
    ACCESS_TOKEN_RETRIEVAL_FAILURE(6000, HttpStatus.FORBIDDEN, "AccessToken 조회 실패"),
    USER_INFO_RETRIEVAL_FAILURE(6001, HttpStatus.FORBIDDEN, "UserInfo 조회 실패"),
    ACCESS_DENIED(6002, HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    UNSUPPORTED_PROVIDER(6003, HttpStatus.BAD_REQUEST, "지원하지 않는 Provider 입니다"),
    DUPLICATE_EMAIL(6004, HttpStatus.BAD_REQUEST, "중복되는 이메일입니다.")
    ;


}