package com.challengeteamkotlin.campdaddy.presentation.product.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity

data class ProductResponse(
    val productId: Long,
    val memberId: Long,
    val title: String,
    val content: String,
    val pricePerDay: Int,
    val imageUrl: List<String>,
    val category: Category,
) {
    companion object {
        fun from(productEntity: ProductEntity) =
            ProductResponse(
                productId = productEntity.id!!,
                memberId = productEntity.member.id!!,
                title = productEntity.title,
                content = productEntity.content,
                pricePerDay = productEntity.pricePerDay,
                imageUrl = productEntity.images.map {
                    it.imageUrl
                },
                category = productEntity.category
            )
    }
}