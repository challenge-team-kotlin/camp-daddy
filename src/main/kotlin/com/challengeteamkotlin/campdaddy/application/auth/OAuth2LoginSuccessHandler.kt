package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.OAuth2UserInfo
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val socialMemberService: SocialMemberService
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        var url = "http://localhost:3000/oauth2/redirect"
        val userInfo = authentication.principal as OAuth2UserInfo
        if (socialMemberService.existMember(OAuth2Provider.valueOf(userInfo.provider), userInfo.providerId)) {
            val accessToken =
                socialMemberService.login(OAuth2Provider.valueOf(userInfo.provider), userInfo.providerId, userInfo.email)
            url += "?token=${accessToken}"
        } else {
            socialMemberService.saveSocialInfo(
                userInfo.email,
                OAuth2Provider.valueOf(userInfo.provider),
                userInfo.providerId
            )
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            url += "?email=${userInfo.email}&provider=${userInfo.provider}&providerId=${userInfo.providerId}"

        }
        redirectStrategy.sendRedirect(request, response, url)
    }
}