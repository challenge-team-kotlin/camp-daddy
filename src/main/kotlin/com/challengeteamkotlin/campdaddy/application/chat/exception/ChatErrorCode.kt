package com.challengeteamkotlin.campdaddy.application.chat.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class ChatErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String
) : ErrorCode {
    CHAT_NOT_FOUND(2001, HttpStatus.BAD_REQUEST, "채팅방을 찾을 수 없습니다"),

    ;
}