package com.challengeteamkotlin.campdaddy.domain.repository.product.dto

data class FindByReservationFilterDto(
        val productId:Long,
        val pricePerDay: Int,
        val title:String,
        val content:String,
        val image: String?,
)
