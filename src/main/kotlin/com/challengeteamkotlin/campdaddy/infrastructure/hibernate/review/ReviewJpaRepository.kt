package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.review

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewJpaRepository : JpaRepository<ReviewEntity, Long> {

    fun existsByProductIdAndMemberId(productId:Long,memberId:Long):Boolean

    fun findByProductId(productId:Long):List<ReviewEntity>?

    fun findByMemberId(memberId: Long):List<ReviewEntity>?

}