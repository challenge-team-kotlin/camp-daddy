package com.challengeteamkotlin.campdaddy.fixture.review

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture

object ReviewFixture {

    val wrongBiggerScoreReview = ReviewEntity("리뷰 내용", 100, MemberFixture.buyer, ProductFixture.tent)
    val wrongSmallerScoreReview = ReviewEntity("리뷰 내용", -100, MemberFixture.buyer, ProductFixture.tent)
    val wrongContentReview = ReviewEntity("", 5, MemberFixture.buyer, ProductFixture.tent)
    val review = ReviewEntity("리뷰 내용", 5, MemberFixture.buyer, ProductFixture.tent)

}