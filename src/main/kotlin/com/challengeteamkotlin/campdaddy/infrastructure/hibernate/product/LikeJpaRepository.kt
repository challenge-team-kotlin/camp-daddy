package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product

import com.challengeteamkotlin.campdaddy.domain.model.product.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LikeJpaRepository: JpaRepository<LikeEntity, Long> {
}