package com.challengeteamkotlin.campdaddy.presentation.product.dto.request

data class EditProductRequest(
        val title: String,
        val content: String,
        val imageUrls: List<String>,
        val price: Long,
        val category: String
)
