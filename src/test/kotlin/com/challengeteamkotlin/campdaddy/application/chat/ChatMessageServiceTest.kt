package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatFailureException
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatMessageFixture.messageRequest
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.existChatRoomId
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ChatMessageServiceTest(
    private val chatMessageService: ChatMessageService = mockk(),
) : BehaviorSpec({

    Given("채팅 메세지 저장 테스트") {
        When("채팅 메세지를 보내는 멤버가 채팅방에 속한 멤버가 아니면") {
            every { chatMessageService.save(any(), any()) } throws ChatFailureException(ChatErrorCode.ACCESS_DENIED)

            Then("예외가 발생한다.") {
                val exception = shouldThrowExactly<ChatFailureException> { chatMessageService.save(existChatRoomId, messageRequest) }

                exception.errorCode shouldBe ChatErrorCode.ACCESS_DENIED
            }
        }
        When("채팅 메세지를 보내는 멤버가 채팅방에 속한 멤버라면") {
            every { chatMessageService.save(existChatRoomId, messageRequest) } returns 1L
            Then("채팅 메세지를 저장한다.") {
                val idForCreatedMessage = chatMessageService.save(existChatRoomId, messageRequest)
                idForCreatedMessage shouldBe 1L
            }
        }
    }
})
