package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepository
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatMessageFixture.chatMessageResponse
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.chatRoomResponse
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.createChatRoomRequest
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.createdChatRoomId
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.existChatRoomId
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.personalChatRoomResponse
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatRoomFixture.wrongChatRoomId
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.memberId
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.productDetail
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import userPrincipal

class ChatRoomServiceTest(
    private val chatRoomService: ChatRoomService = mockk(),
    private val chatMessageRepository: ChatMessageRepository = mockk(),
) : BehaviorSpec({

    Given("채팅방 생성 테스트") {

        When("멤버와 상품이 있다는 가정하에 채팅방이 이미 생성되어 있으면") {
            every { chatRoomService.createChat(any()) } returns existChatRoomId
            val chatRoomId = chatRoomService.createChat(createChatRoomRequest)

            Then("해당 채팅방의 아이디를 리턴한다.") {
                chatRoomId shouldBe existChatRoomId
            }
        }

        When("멤버와 상품이 있다는 가정하에 채팅방이 존재하지 않으면") {
            every { chatRoomService.createChat(createChatRoomRequest) } returns createdChatRoomId
            val newChatRoomId = chatRoomService.createChat(createChatRoomRequest)

            Then("새로운 채팅방을 만들고, 아이디를 리턴한다.") {
                newChatRoomId shouldBe createdChatRoomId
            }
        }
    }

    Given("멤버 개인 채팅방 리스트 조회 테스트") {

        When("유저의 ID로 조회되는 채팅방이 없으면") {
            every { chatRoomService.getPersonalChatList(memberId, userPrincipal) } returns emptyList()

            Then("빈 리스트를 리턴한다.") {
                val emptyList = chatRoomService.getPersonalChatList(memberId, userPrincipal)
                emptyList shouldBe emptyList()
            }
        }

        When("유저의 ID로 조회되는 채팅방이 있으면") {
            every { chatRoomService.getPersonalChatList(memberId, userPrincipal) } returns personalChatRoomResponse
            val personalChatRoomResponse = chatRoomService.getPersonalChatList(memberId, userPrincipal)

            Then("상대방 닉네임, 상품 이미지, 마지막 메세지, 마지막 메세지의 날짜 정보를 가진 채팅방 리스트를 리턴한다.") {
                personalChatRoomResponse?.map {
                    it shouldNotBe null
                }
            }
        }
    }

    Given("채팅방 조회 테스트") {

        When("채팅방 ID로 조회할 때 채팅방이 존재하면") {
            every { chatRoomService.getChatDetail(any(), any()) } returns chatRoomResponse
            val existChatRoomResponse =  chatRoomService.getChatDetail(existChatRoomId, userPrincipal)
            Then("채팅방의 메세지, 상품 정보와 함께 방을 리턴한다.") {
                existChatRoomResponse.chatHistory shouldBe chatMessageResponse
                existChatRoomResponse.productDetail shouldBe productDetail
            }
        }

        When("채팅방 ID로 조회할 때 채팅방이 존재하지 않으면") {
            every { chatRoomService.getChatDetail(wrongChatRoomId, userPrincipal) } throws EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)

            Then("예외가 던져진다.") {
                val exception = shouldThrowExactly<EntityNotFoundException> { chatRoomService.getChatDetail(wrongChatRoomId, userPrincipal) }

                exception.errorCode shouldBe ChatErrorCode.CHAT_NOT_FOUND
            }
        }
    }

    Given("채팅방 삭제 테스트") {

        When("채팅방 ID로 삭제를 시도할 때 채팅방이 존재하면") {
            every { chatRoomService.removeChat(existChatRoomId, userPrincipal) } just runs
            every { chatRoomService.getChatDetail(existChatRoomId, userPrincipal) } throws EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)
            every { chatMessageRepository.getChatMessageByChatRoomId(existChatRoomId) } returns emptyList()
            Then("해당 채팅방과 안에 담긴 메세지는 모두 삭제되고, 재조회는 되지 않는다") {
                val emptyChatHistory = chatMessageRepository.getChatMessageByChatRoomId(existChatRoomId)
                val exception = shouldThrowExactly<EntityNotFoundException> { chatRoomService.getChatDetail(existChatRoomId, userPrincipal) }

                exception.errorCode shouldBe ChatErrorCode.CHAT_NOT_FOUND

                emptyChatHistory shouldBe emptyList()
            }
        }
        When("채팅방 ID로 삭제를 시도할 때 채팅방이 존재하지 않으면") {
            every { chatRoomService.getChatDetail(wrongChatRoomId, userPrincipal) } throws EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)
            Then("예외가 던져진다.") {
                val exception = shouldThrowExactly<EntityNotFoundException> { chatRoomService.getChatDetail(wrongChatRoomId, userPrincipal) }

                exception.errorCode shouldBe ChatErrorCode.CHAT_NOT_FOUND
            }
        }
    }
})
