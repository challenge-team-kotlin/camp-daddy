package com.challengeteamkotlin.campdaddy.domain.model.review

import com.challengeteamkotlin.campdaddy.fixture.review.ReviewImageFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldNotMatch

class ReviewImageEntityTest : BehaviorSpec({
    val urlRegex = Regex("^((http|https)://)[-a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)$")

    Given("리뷰 이미지 Entity 생성 테스트") {
        When("리뷰 이미지 Entity의 image가 빈 값이라면") {
            Then("새로운 리뷰 이미지 Entity를 만들 수 없다.") {
                ReviewImageFixture.wrongBlankReviewImage.image shouldBe ""
            }
        }

        When("리뷰 이미지 Entity의 image가 url 형식이 아니라면"){
            Then("새로운 리뷰 Entity를 만들 수 없다"){
                ReviewImageFixture.wrongUrlReviewImage.image shouldNotMatch urlRegex.toString()
            }
        }

        When("리뷰 이미지 Entity의 모든 형식을 만족하면") {
            val reviewImage = ReviewImageFixture.reviewImage
            Then("새로운 리뷰 Entity를 만들 수 있다.") {
                reviewImage.image shouldNotBe ""
                reviewImage.image shouldMatch urlRegex
            }
        }
    }
})