package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageJpaRepository : JpaRepository<ProductImageEntity, Long> {
}