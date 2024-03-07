package com.challengeteamkotlin.campdaddy.application.member.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String,
) : ErrorCode {
    ACCESS_TOKEN_RETRIEVAL_FAILURE(1000, HttpStatus.FORBIDDEN, "AccessToken 조회 실패"),
    USER_INFO_RETRIEVAL_FAILURE(1001, HttpStatus.FORBIDDEN, "UserInfo 조회 실패"),

    ;


}