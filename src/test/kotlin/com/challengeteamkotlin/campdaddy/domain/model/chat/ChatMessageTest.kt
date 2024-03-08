package com.challengeteamkotlin.campdaddy.domain.model.chat

import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.chatRoom
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.buyer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ChatMessageTest : BehaviorSpec({

    Given("채팅 메시지 생성 테스트") {

        When("새로운 메세지가 생성될 때 공백이 아니면") {
            var chat = ChatMessageEntity("빌려주삼", buyer, chatRoom,MessageStatus.MESSAGE)

            Then("메세지가 저장된다.") {
                chat.message shouldBe "빌려주삼"
                chat.message shouldNotBe ""
                chat.chatRoom.member shouldBe buyer
                chat.member shouldBe buyer
            }
        }
    }
})
