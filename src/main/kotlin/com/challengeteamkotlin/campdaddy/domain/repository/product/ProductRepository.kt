package com.challengeteamkotlin.campdaddy.domain.repository.product

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product.ProductJpaRepository


interface ProductRepository : ProductJpaRepository {

    fun findByMemberId(memberId:Long):List<ProductEntity>
}
