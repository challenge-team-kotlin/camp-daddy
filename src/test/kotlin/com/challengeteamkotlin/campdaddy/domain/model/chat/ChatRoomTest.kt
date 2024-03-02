package com.challengeteamkotlin.campdaddy.domain.model.chat

import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.chatRoom
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.wrongChatRoom
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.buyer
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.seller
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ChatRoomTest : BehaviorSpec({

    Given("채팅룸 생성 테스트") {
        When("채팅룸을 만든 멤버가 판매자라면") {
            Then("채팅룸이 생성되지 않는다.") {
                 wrongChatRoom.validateRoomOwner() shouldBe false
            }
        }
        When("채팅룸을 만든 멤버가 구매자라면") {
            shouldNotThrow<IllegalArgumentException> { chatRoom }
            Then("채팅룸이 생성된다.") {
                chatRoom.memberEntity shouldBe buyer
                chatRoom.productEntity.memberEntity shouldBe seller
            }
        }
    }
})