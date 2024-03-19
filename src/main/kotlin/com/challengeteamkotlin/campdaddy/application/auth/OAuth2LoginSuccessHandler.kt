package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberRole
import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtPlugin
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.OAuth2UserInfo
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val jwtPlugin: JwtPlugin,
): AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val userInfo = authentication.principal as OAuth2UserInfo
        val accessToken = jwtPlugin.generateAccessToken(
            subject = userInfo.memberId.toString(),
            email = userInfo.email,
            role = MemberRole.MEMBER.name
        )
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(accessToken)
    }
}