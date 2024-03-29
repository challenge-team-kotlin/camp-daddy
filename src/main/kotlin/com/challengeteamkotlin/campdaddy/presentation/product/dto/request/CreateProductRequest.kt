package com.challengeteamkotlin.campdaddy.presentation.product.dto.request

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import org.springframework.web.multipart.MultipartFile

data class CreateProductRequest(
        val title: String,
        val content: String,
        val pricePerDay: Long,
        val images: List<String>,
        val category: String,

        ) {
    fun from(member: MemberEntity): ProductEntity {
        return ProductEntity(
                member = member,
                pricePerDay = this.pricePerDay,
                title = this.title,
                content = this.content,
                category = Category.valueOf(category)
        )
    }
}
