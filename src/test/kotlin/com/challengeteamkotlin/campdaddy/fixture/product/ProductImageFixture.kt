package com.challengeteamkotlin.campdaddy.fixture.product

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.lamp
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.tent

object ProductImageFixture {
    val productImage1 = ProductImageEntity(lamp, "testImage1")
    val productImage2 = ProductImageEntity(tent, "testImage2")
    val productImage3 = ProductImageEntity(tent, "testImage2")
}