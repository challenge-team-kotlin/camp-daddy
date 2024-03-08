package com.challengeteamkotlin.campdaddy.fixture.auth

import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.KakaoUserAccountResponse
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.KakaoUserInfoResponse
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.KakaoUserPropertiesResponse

object AuthFixture {
    // KAKAO Request
    val kakaoRegisterInfo = KakaoUserInfoResponse(
        id = 1L,
        properties = KakaoUserPropertiesResponse("이승상"),
        kakaoAccount = KakaoUserAccountResponse("seungsang@kakao.com", "이승상", "010-1234-5678")
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
