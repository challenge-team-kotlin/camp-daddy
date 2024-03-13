package com.challengeteamkotlin.campdaddy.presentation.product.dto.request

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import java.time.LocalDate

data class SearchProductRequest(
        val startDate: LocalDate,
        val endDate: LocalDate,
        val category: Category,
        val filterReservation: Boolean,
        val search: String?,
)