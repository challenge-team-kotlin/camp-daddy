package com.challengeteamkotlin.campdaddy.fixture.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.MessageStatus
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime

object ChatMessageFixture {
    // Request
    val messageRequest = MessageRequest(200, "안녕하세요", MessageStatus.MESSAGE)

    val chatPageRequest = PageRequest.of(0, 20)
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