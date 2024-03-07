package com.challengeteamkotlin.campdaddy.fixture.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.buyer
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.seller
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.tent

object ChatRoomFixture {
    val chatRoom = ChatRoomEntity(buyer = buyer, seller = seller, product = tent)
}