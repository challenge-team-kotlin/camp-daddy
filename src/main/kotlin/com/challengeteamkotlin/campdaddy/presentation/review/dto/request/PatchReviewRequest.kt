package com.challengeteamkotlin.campdaddy.presentation.review.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull

data class PatchReviewRequest(
    @field:NotNull
    @field:NotEmpty
    val content: String,

    @field:NotNull
    @field:Min(0)
    @field:Max(5)
    val score: Int,

    val reviewImageUrls: List<String> = emptyList()

)