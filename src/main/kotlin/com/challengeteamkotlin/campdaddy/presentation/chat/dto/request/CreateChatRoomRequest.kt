package com.challengeteamkotlin.campdaddy.presentation.chat.dto.request

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity

data class CreateChatRoomRequest(
    val productId: Long,
    val buyerId: Long
) {
    fun of(buyer: MemberEntity, seller: MemberEntity, productEntity: ProductEntity) =
        ChatRoomEntity(buyer, seller, productEntity)
}
