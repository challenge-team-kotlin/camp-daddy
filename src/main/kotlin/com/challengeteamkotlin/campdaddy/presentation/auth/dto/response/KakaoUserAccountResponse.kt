package com.challengeteamkotlin.campdaddy.presentation.auth.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoUserAccountResponse(
    val email: String,
    val name: String,
    val phoneNumber: String,
)