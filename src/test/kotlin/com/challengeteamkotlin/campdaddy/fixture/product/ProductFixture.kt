package com.challengeteamkotlin.campdaddy.fixture.product

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.seller
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.ProductDetailResponse

object ProductFixture {
    // Entity
    val lamp = ProductEntity(seller, 20000, "불이 잘 들어오는 램프", "잘 들어 옵니다.", Category.LAMPS)
    val tent = ProductEntity(seller, 100_000, "텐트 빌려드려요", "텐트 빌려드림ㅇㅇ", Category.TENTS)
    val tent1 = ProductEntity(seller, 100_000, "군대 A형 텐트", "군대에서 전역할때 훔쳐온 A형 텐트입니다.", Category.TENTS)

    // Response
    val productDetail = ProductDetailResponse(
        title = tent.title,
        images = "http://localhost:8080/images/blah.png",
        content = tent.content,
        pricePerDay = 100_000
    )
}