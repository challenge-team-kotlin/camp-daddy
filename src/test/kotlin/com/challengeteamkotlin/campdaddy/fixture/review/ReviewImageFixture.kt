package com.challengeteamkotlin.campdaddy.fixture.review

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewImageEntity
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture

object ReviewImageFixture {

    val wrongBlankReviewImage = ReviewImageEntity(ReviewFixture.review, "")
    val wrongUrlReviewImage = ReviewImageEntity(ReviewFixture.review, "wrong wrong")
    val reviewImage = ReviewImageEntity(ReviewFixture.review, "https://www.notion.so/icons/verified_green.svg")

}