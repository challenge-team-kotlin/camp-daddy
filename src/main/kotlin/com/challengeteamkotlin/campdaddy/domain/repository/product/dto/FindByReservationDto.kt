package com.challengeteamkotlin.campdaddy.domain.repository.product.dto

data class FindByReservationDto(
        val productId: Long,
        val pricePerDay: Long,
        val title: String,
        val content: String,
        val image: String?,
        val checkReservation: Long,
)