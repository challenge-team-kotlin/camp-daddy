package com.challengeteamkotlin.campdaddy.presentation.review.dto.request

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull

data class CreateReviewRequest(
        @NotEmpty
        val content: String,
        @NotNull
        val productId: Long,
        @NotNull
        @Min(0)
        @Max(5)
        val score: Int,

        val imageUrls: List<String>?

) {
    @JsonIgnore
    var memberId: Long? = null

    fun of(productEntity: ProductEntity, memberEntity: MemberEntity): ReviewEntity {
        return ReviewEntity(
                content = content,
                score = score,
                product = productEntity,
                member = memberEntity,
        )
    }
}