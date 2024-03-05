package com.challengeteamkotlin.campdaddy.fixture.product

import com.challengeteamkotlin.campdaddy.domain.model.product.LikeEntity
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.lamp
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.buyer

object LikeFixture {
    val like = LikeEntity(lamp, buyer)
}
