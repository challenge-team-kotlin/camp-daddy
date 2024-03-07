package com.challengeteamkotlin.campdaddy.infrastructure.client.kakao

import com.challengeteamkotlin.campdaddy.infrastructure.client.properties.KakaoProviderProperties
import com.challengeteamkotlin.campdaddy.infrastructure.client.properties.KakaoRegistrationProperties
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.KakaoTokenResponse
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.KakaoUserInfoResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoOAuth2Client(
    private val registration: KakaoRegistrationProperties,
    private val provider: KakaoProviderProperties,
    private val restClient: RestClient
) {

    fun generateLoginPageUrl(): String {
        return StringBuilder(provider.authorizationUrl)
            .append("?client_id=").append(registration.clientId)
            .append("&redirect_uri=").append(registration.redirectUrl)
            .append("&response_type=").append("code")
            .toString()
    }

    fun getAccessToken(authorizationCode: String): String {
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to registration.clientId,
            "code" to authorizationCode,
            "client_secret" to registration.clientSecret,
        )
        return restClient.post()
            .uri(provider.tokenUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .body<KakaoTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("AccessToken 조회 실패")
    }

    fun retrieveUserInfo(accessToken: String): KakaoUserInfoResponse {
        return restClient.get()
            .uri(provider.userInfoUrl)
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .body<KakaoUserInfoResponse>()
            ?: throw RuntimeException("UserInfo 조회 실패")
    }
}