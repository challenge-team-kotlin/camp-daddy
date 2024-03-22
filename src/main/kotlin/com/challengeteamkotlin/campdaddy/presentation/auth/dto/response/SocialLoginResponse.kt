package com.challengeteamkotlin.campdaddy.presentation.auth.dto.response

data class SocialLoginResponse(
    val email: String,
    val provider: String,
    val providerId: String,
) {
    companion object {
        fun of(email: String, provider: String, providerId: String): SocialLoginResponse {
            return SocialLoginResponse(
                email = email,
                provider = provider,
                providerId = providerId
            )
        }
    }
}