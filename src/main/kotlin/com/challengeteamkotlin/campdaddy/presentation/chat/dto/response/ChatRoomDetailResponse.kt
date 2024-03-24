package com.challengeteamkotlin.campdaddy.presentation.chat.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.ProductDetailResponse
import org.springframework.data.domain.Slice

data class ChatRoomDetailResponse(
    val productDetail: ProductDetailResponse,
    val chatHistory: List<MessageResponse>,
    val hasNext: Boolean
) {
    companion object {
        fun of(chatRoom: ChatRoomEntity, chatMessagePage: Slice<MessageResponse>) = ChatRoomDetailResponse(
            productDetail = ProductDetailResponse.from(chatRoom.product),
            chatHistory = chatMessagePage.content,
            hasNext = chatMessagePage.hasNext()
        )
    }
}
