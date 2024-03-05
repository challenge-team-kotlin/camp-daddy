package com.challengeteamkotlin.campdaddy.fixture.review

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewImageEntity

object ReviewImageFixture {

    val wrongBlankReviewImage = ReviewImageEntity(ReviewFixture.review, "")
    val wrongUrlReviewImage = ReviewImageEntity(ReviewFixture.review, "wrong wrong")
    val reviewImage = ReviewImageEntity(ReviewFixture.review, "https://www.notion.so/icons/verified_green.svg")

}