package com.challengeteamkotlin.campdaddy.presentation.product.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity

data class SearchProductResponse(
        val productId: Long,
        val title: String,
        val content: String,
        val memberNickName: String,
        val pricePerDay: Long,
        val imageUrl: String,
) {
    companion object {
        fun of(productEntity: ProductEntity): SearchProductResponse =
                SearchProductResponse(
                        productId = productEntity.id!!,
                        title = productEntity.title,
                        content = productEntity.content,
                        memberNickName = productEntity.member.nickname,
                        pricePerDay = productEntity.pricePerDay,
                        imageUrl = productEntity.images[0].imageUrl
                )

    }
}
