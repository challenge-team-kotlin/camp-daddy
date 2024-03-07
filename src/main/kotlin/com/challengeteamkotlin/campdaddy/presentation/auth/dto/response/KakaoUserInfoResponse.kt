package com.challengeteamkotlin.campdaddy.presentation.auth.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoUserInfoResponse(
    val id: Long,
    val properties: KakaoUserPropertiesResponse,
    val kakaoAccount: KakaoUserAccountResponse
) {
    val nickname: String
        get() = properties.nickname
    val phoneNumber: String
        get() = kakaoAccount.phoneNumber
    val email: String
        get() = kakaoAccount.email
    val name: String
        get() = kakaoAccount.name
}