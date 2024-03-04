package com.challengeteamkotlin.campdaddy.domain.model.product

import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture.product
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.seller
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ProductTest : BehaviorSpec ({
    Given("상품 생성 테스트.") {
        Then("생성 완료."){
                product.memberEntity shouldBe seller
        }
    }
}){
}