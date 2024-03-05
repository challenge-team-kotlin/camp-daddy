package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.review

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewJpaRepository : JpaRepository<ReviewEntity, Long> {
}