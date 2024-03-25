package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberRole
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtPlugin
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.OAuth2UserInfo
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.SocialLoginResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val jwtPlugin: JwtPlugin,
    private val socialMemberService: SocialMemberService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val userInfo = authentication.principal as OAuth2UserInfo
        val existMember =
            socialMemberService.existMember(OAuth2Provider.valueOf(userInfo.provider), userInfo.providerId)
        if (existMember) {
            val member = socialMemberService.findMember(OAuth2Provider.valueOf(userInfo.provider), userInfo.providerId)
            val accessToken = jwtPlugin.generateAccessToken(
                subject = member?.id.toString(),
                email = userInfo.email,
                role = MemberRole.MEMBER.name,
                response = response
            )
            response.addHeader("Authorization", "Bearer $accessToken")
            response.contentType = MediaType.APPLICATION_JSON_VALUE
        } else {
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.writer.write(
                jacksonObjectMapper().writeValueAsString(
                    SocialLoginResponse.of(
                        userInfo.email,
                        userInfo.provider,
                        userInfo.providerId
                    )
                )
            )
        }
    }
}