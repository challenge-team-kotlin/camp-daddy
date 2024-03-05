package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductEntity, Long> {
}