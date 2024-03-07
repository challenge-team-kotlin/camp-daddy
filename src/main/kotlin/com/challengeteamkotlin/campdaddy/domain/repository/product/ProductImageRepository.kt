package com.challengeteamkotlin.campdaddy.domain.repository.product

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product.ProductImageJpaRepository

interface ProductImageRepository: ProductImageJpaRepository {

    fun findAllByProductId(productId:Long):List<ProductImageEntity>
}