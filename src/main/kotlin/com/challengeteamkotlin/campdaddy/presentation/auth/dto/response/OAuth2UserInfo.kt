package com.challengeteamkotlin.campdaddy.presentation.auth.dto.response

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.application.auth.exception.UnsupportedProviderException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2UserInfo(
    var id: String,
    val provider: String,
    val email: String,
    @get:JvmName("name")
    val name: String
) : OAuth2User {

    override fun getName(): String {
        return "$provider : $id"
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
    val name = account["name"] ?: ""

    return OAuth2UserInfo(
        id = (originUser.attributes[userNameAttributeName] as Long).toString(),
        provider = provider.uppercase(),
        email = email as String,
        name = name as String,
    )
}

private fun ofNaver(provider: String, userRequest: OAuth2UserRequest, originUser: OAuth2User): OAuth2UserInfo {
    val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
    val response = originUser.attributes[userNameAttributeName] as Map <*, *>
    val email = response["email"] ?: ""
    val name = response["name"] ?: ""
    val id = response["id"] ?: ""

    return OAuth2UserInfo(
        id = id as String,
        provider = provider.uppercase(),
        email = email as String,
        name = name as String,
    )
}

private fun ofGoogle(provider: String, userRequest: OAuth2UserRequest, originUser: OAuth2User): OAuth2UserInfo {
    val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
    val email = originUser.attributes["email"] ?: ""
    val name = originUser.attributes["name"] ?: ""

    return OAuth2UserInfo(
        id = originUser.attributes[userNameAttributeName]?.toString() ?: "",
        provider = provider.uppercase(),
        email = email as String,
        name = name as String,
    )
}