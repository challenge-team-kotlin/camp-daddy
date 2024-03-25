package com.challengeteamkotlin.campdaddy.fixture.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.fixture.chat.ChatMessageFixture.chatMessageResponse
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.buyer
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.seller
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.productDetail
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.tent
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.CreateChatRoomRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomDetailResponse
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse
import java.time.LocalDateTime

object ChatRoomFixture {
    // ID
    val existChatRoomId = 1L
    val createdChatRoomId = 1L
    val wrongChatRoomId = 999L

    // Request
    val createChatRoomRequest = CreateChatRoomRequest(productId = 1, buyerId = 1)

    // Response
    val personalChatRoomResponseOfFirst =
        ChatRoomResponse("unknown", "http://localhgst:8080/image/blah.png", "구매 하시나요?", LocalDateTime.now())

    val personalChatRoomResponseOfSecond =
        ChatRoomResponse("unknown", "http://localhgst:8080/image/blah.png", "구매 하시나요?", LocalDateTime.now())

    val personalChatRoomResponseOfThird =
        ChatRoomResponse("unknown", "http://localhgst:8080/image/blah.png", "구매 하시나요?", LocalDateTime.now())

    val personalChatRoomResponse: List<ChatRoomResponse> = listOf(
        personalChatRoomResponseOfFirst,
        personalChatRoomResponseOfSecond,
        personalChatRoomResponseOfThird
    )

    val chatRoomResponse = ChatRoomDetailResponse(
        productDetail = productDetail,
        chatHistory = chatMessageResponse,
        hasNext = true
    )

    // Entity
    val chatRoom = ChatRoomEntity(buyer = buyer, seller = seller, product = tent)
}