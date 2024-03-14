package com.challengeteamkotlin.campdaddy.fixture.auth

import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.OAuth2UserInfo

object AuthFixture {
    //test provider
    val wrongProvider = "wrongProvider"
    //test
    val newAuthInfo = OAuth2UserInfo(
        id = "1234",
        provider = OAuth2Provider.KAKAO.name,
        email = "test@test.com",
        name = "test"
    )

    val existAuthInfo = OAuth2UserInfo(
        id = "1234",
        provider = OAuth2Provider.KAKAO.name,
        email = "test@test.com",
        name = "test"
    )
    // KAKAO Request
    val oAuth2KakaoUserInfo = OAuth2UserInfo(
        id = "1234",
        provider = OAuth2Provider.KAKAO.name,
        email = "exist@mail.com",
        name = "exist"
    )

    // UserPrincipal
    val userPrincipalForAccessDenied = UserPrincipal(999, "accessdenied@kakao.com", setOf("MEMBER"))
    val userPrincipalForDeleted = UserPrincipal(999, "deleted@mail.com", setOf("MEMBER"))

    // accessToken
    val socialAccessToken = "socialAccessToken"
    val failedAccessToken = "fail"

    // jwtToken, jwtTokenNickname
    val succeedJwtToken = "success"
}
