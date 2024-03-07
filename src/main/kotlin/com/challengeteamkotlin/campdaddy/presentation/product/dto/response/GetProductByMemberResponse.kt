package com.challengeteamkotlin.campdaddy.presentation.product.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import java.time.LocalDateTime

data class GetProductByMemberResponse(
        val productId:Long,
        val presentationImage:String,
        val createdAt:LocalDateTime,
        val title:String,
        val likeCount:Int
        ) {
        companion object{
                fun of(productEntity: ProductEntity, likeCount:Int):GetProductByMemberResponse{
                        return GetProductByMemberResponse(
                                productId = productEntity.id!!,
                                title = productEntity.title,
                                createdAt = productEntity.createdAt,
                                presentationImage = productEntity.images[0].imageUrl,
                                likeCount = likeCount
                        )
                }
        }

}