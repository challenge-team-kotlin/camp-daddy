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
    MEMBER_NOT_FOUND(1002, HttpStatus.BAD_REQUEST, "조회 된 회원이 없습니다."),
    ACCESS_DENIED(1003, HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    ;


}