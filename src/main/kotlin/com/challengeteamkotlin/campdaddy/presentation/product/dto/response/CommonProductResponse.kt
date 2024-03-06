package com.challengeteamkotlin.campdaddy.presentation.product.dto.response

import java.time.LocalDateTime

abstract class CommonProductResponse(
    val title: String,
    val price : Int,
    val like: Int,
    val createdAt : LocalDateTime,

) {
}