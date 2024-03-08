package com.challengeteamkotlin.campdaddy.presentation.product.dto.request

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import java.time.LocalDate

data class SearchProductRequest(
    var startDate:LocalDate,
    var endDate: LocalDate,
    var preferRegion :String,
    var category:Category,
    var filterReservation: Boolean,
    var lastProductId:Long
)