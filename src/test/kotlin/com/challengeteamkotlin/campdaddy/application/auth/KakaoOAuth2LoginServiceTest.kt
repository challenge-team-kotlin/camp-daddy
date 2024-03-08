package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.fixture.auth.AuthFixture.socialAccessToken
import com.challengeteamkotlin.campdaddy.fixture.auth.AuthFixture.succeedJwtToken
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class KakaoOAuth2LoginServiceTest(
    private val kakaoOAuth2LoginService: KakaoOAuth2LoginService = mockk(),
) : BehaviorSpec({

    Given("소셜 로그인 테스트") {
        When("소셜 멤버의 정보를 성공적으로 가져오고, Member 저장을 마치면") {
            every { kakaoOAuth2LoginService.login(any()) } returns succeedJwtToken
            Then("JWT Token을 발급한다") {
                val accessToken = kakaoOAuth2LoginService.login(socialAccessToken)
                accessToken shouldBe succeedJwtToken
            }
        }
    }
})
