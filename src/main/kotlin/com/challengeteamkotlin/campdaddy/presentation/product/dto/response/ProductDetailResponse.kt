package com.challengeteamkotlin.campdaddy.presentation.product.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity

data class ProductDetailResponse(
        val title: String,
        val images: String,
        val content: String,
        val pricePerDay: Long,
) {
    companion object {
        fun from(productEntity: ProductEntity) = ProductDetailResponse(
                productEntity.title,
                productEntity.images[0].imageUrl,
                productEntity.content,
                productEntity.pricePerDay
        )
    }
}
