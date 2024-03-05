package com.challengeteamkotlin.campdaddy.domain.model.product

import io.kotest.core.spec.style.BehaviorSpec
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.lamp
import com.challengeteamkotlin.campdaddy.fixture.product.ProductImageFixture.productImage1
import com.challengeteamkotlin.campdaddy.fixture.product.ProductImageFixture.productImage2
import io.kotest.matchers.shouldBe

class ProductEntityTest : BehaviorSpec({
    Given("상품 생성 테스트") {
        When("이미지를 올린다면?") {
            Then("게시글이 생성된다.") {
                lamp.uploadImage(productImage1)
                lamp.images[0] shouldBe productImage1
            }
        }

        When("여러이미지를 올린다면?") {
            Then("게시글이 생성된다.") {
                lamp.uploadImage(productImage1)
                lamp.uploadImage(productImage2)
                lamp.images.size shouldBe 2
            }
        }
    }
})

