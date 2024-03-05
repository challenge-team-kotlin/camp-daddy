package com.challengeteamkotlin.campdaddy.domain.model.product

import io.kotest.core.spec.style.BehaviorSpec
import com.challengeteamkotlin.campdaddy.fixture.product.LikeFixture.like
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.buyer
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture.seller
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


class LikeEntityTest : BehaviorSpec({
    Given("Like 생성 테스트") {
        When("좋아요를 작성자가 누른다면?") {
            Then("좋아요가 생성되지 않는다.") {
                like.memberEntity shouldNotBe seller
            }
        }

        When("좋아요를 다른이가 누른다면?") {
            Then("좋아요가 생성된다.") {
                like.memberEntity shouldBe buyer
            }
        }
    }
})