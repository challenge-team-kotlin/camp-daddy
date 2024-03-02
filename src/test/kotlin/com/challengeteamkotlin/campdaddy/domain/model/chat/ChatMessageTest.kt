package com.challengeteamkotlin.campdaddy.domain.model.chat

import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.chatRoom
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.buyer
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ChatMessageTest : BehaviorSpec({

    Given("채팅 메시지 생성 테스트") {

        When("새로운 메세지가 생성될 때 공백이 아니면") {
            lateinit var chat: ChatMessageEntity

            shouldNotThrow<IllegalArgumentException> { ChatMessageEntity("빌려주삼", buyer, chatRoom).also { chat = it } }

            Then("메세지가 저장된다.") {
                chat.message shouldBe "빌려주삼"
                chat.chatRoomEntity.memberEntity shouldBe buyer
                chat.memberEntity shouldBe buyer
            }
        }

        When("새로운 메세지가 생성될 때 공백이면") {
            Then("메세지가 저장되지 않는다.") {
                shouldThrowExactly<IllegalArgumentException> { ChatMessageEntity("", buyer, chatRoom) }
            }
        }
    }
})
