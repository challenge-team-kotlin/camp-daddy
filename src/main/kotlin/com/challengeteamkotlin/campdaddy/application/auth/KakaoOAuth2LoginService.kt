package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.infrastructure.client.kakao.KakaoOAuth2Client
import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtPlugin
import org.springframework.stereotype.Service

@Service
class KakaoOAuth2LoginService(
    private val kakaoOAuth2Client: KakaoOAuth2Client,
    private val socialMemberService: SocialMemberService,
    private val jwtPlugin: JwtPlugin
) {
    fun login(authorizationCode: String): String {
        return kakaoOAuth2Client.getAccessToken(authorizationCode)
            .let { kakaoOAuth2Client.retrieveUserInfo(it) }
            .let { socialMemberService.registerIfAbsent(it) }
            .let { jwtPlugin.generateAccessToken(it.id!!.toString(), it.nickname) }
    }
}