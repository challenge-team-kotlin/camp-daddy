package com.challengeteamkotlin.campdaddy.presentation.chat.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.ProductDetailResponse

data class ChatRoomDetailResponse(
    val productDetail: ProductDetailResponse,
    val chatHistory: List<MessageResponse>,
) {
    companion object {
        fun of(chatRoom: ChatRoomEntity, chatHistory: List<MessageResponse>) = ChatRoomDetailResponse(
            productDetail = ProductDetailResponse.from(chatRoom.product),
            chatHistory = chatHistory
        )
    }
}
