package com.challengeteamkotlin.campdaddy.fixture.product

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.lamp

object ProductImageFixture {
    val productImage1 = ProductImageEntity(lamp, "testImage1")
    val productImage2 = ProductImageEntity(lamp, "testImage2")

}