package com.challengeteamkotlin.campdaddy.presentation.auth.dto.response

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.application.auth.exception.UnsupportedProviderException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2UserInfo(
    var providerId: String,
    val provider: String,
    val email: String,
) : OAuth2User {
    override fun getName(): String {
        return "$provider : $providerId"
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    companion object {
        fun of(provider: String, userRequest: OAuth2UserRequest, originUser: OAuth2User): OAuth2UserInfo {
            return when(provider) {
                "KAKAO", "kakao" -> ofKakao(provider, userRequest, originUser)
                "NAVER", "naver" -> ofNaver(provider, userRequest, originUser)
                "GOOGLE", "google" -> ofGoogle(provider, userRequest, originUser)
                else -> throw UnsupportedProviderException(AuthErrorCode.UNSUPPORTED_PROVIDER)
            }
        }
    }
}
private fun ofKakao(provider: String, userRequest: OAuth2UserRequest, originUser: OAuth2User): OAuth2UserInfo {
    val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
    val account = originUser.attributes["kakao_account"] as Map<*, *>
    val email = account["email"] ?: ""


    return OAuth2UserInfo(
        providerId = (originUser.attributes[userNameAttributeName] as Long).toString(),
        provider = provider.uppercase(),
        email = email as String,
    )
}

private fun ofNaver(provider: String, userRequest: OAuth2UserRequest, originUser: OAuth2User): OAuth2UserInfo {
    val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
    val response = originUser.attributes[userNameAttributeName] as Map <*, *>
    val email = response["email"] ?: ""
    val providerId = response["id"] ?: ""

    return OAuth2UserInfo(
        providerId = providerId as String,
        provider = provider.uppercase(),
        email = email as String,
    )
}

private fun ofGoogle(provider: String, userRequest: OAuth2UserRequest, originUser: OAuth2User): OAuth2UserInfo {
    val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
    val email = originUser.attributes["email"] ?: ""

    return OAuth2UserInfo(
        providerId = originUser.attributes[userNameAttributeName]?.toString() ?: "",
        provider = provider.uppercase(),
        email = email as String,
    )
}