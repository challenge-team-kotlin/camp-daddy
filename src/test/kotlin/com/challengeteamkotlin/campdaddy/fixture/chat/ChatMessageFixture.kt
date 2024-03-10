package com.challengeteamkotlin.campdaddy.fixture.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.MessageStatus
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.memberId
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.wrongMemberId
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import java.time.LocalDateTime

object ChatMessageFixture {
    // Request
    val messageRequestForPass = MessageRequest(wrongMemberId, "안녕하세요", MessageStatus.MESSAGE)
    val messageRequestForFail = MessageRequest(memberId, "안녕하세요", MessageStatus.MESSAGE)

    // Response
    val messageResponseFirst = MessageResponse("unknown", "빌리고 싶습니다", LocalDateTime.now())
    val messageResponseSecond = MessageResponse("unknown", "빌리고 싶습니다", LocalDateTime.now())
    val messageResponseThird = MessageResponse("unknown", "빌리고 싶습니다", LocalDateTime.now())

    val chatMessageResponse = listOf(
        messageResponseFirst,
        messageResponseSecond,
        messageResponseThird
    )
}