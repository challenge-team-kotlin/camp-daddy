package com.challengeteamkotlin.campdaddy.fixture.product

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.seller

object ProductFixture {
    val lamp = ProductEntity(seller, 20000, "불이 잘 들어오는 램프", "잘 들어 옵니다.", Category.LAMPS)
    val tent = ProductEntity(seller, 100_000, "텐트 빌려드려요", "텐트 빌려드림ㅇㅇ", Category.TENTS)

}