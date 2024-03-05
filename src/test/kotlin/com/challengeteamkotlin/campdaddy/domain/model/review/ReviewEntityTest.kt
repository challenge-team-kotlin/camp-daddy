package com.challengeteamkotlin.campdaddy.domain.model.review

import com.challengeteamkotlin.campdaddy.fixture.review.ReviewFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ReviewEntityTest : BehaviorSpec({
    Given("리뷰 Entity 생성 테스트") {
        When("리뷰 Entity의 점수가 0보다 작거나 10보다 크면") {
            Then("새로운 리뷰 Entity를 만들 수 없다.") {
                ReviewFixture.wrongSmallerScoreReview.score shouldBeLessThan 0
                ReviewFixture.wrongBiggerScoreReview.score shouldBeGreaterThan 10
            }

        }

        When("리뷰 Entity의 Content가 빈 값이라면"){
            Then("새로운 리뷰 Entity를 만들 수 없다"){
                ReviewFixture.wrongContentReview.content shouldBe ""
            }
        }

        When("리뷰 Entity의 모든 형식을 만족하면") {
            val review = ReviewFixture.review
            Then("새로운 리뷰 Entity를 만들 수 있다.") {
                review.content shouldNotBe ""
                review.score shouldBeInRange 0..10
            }
        }
    }
})