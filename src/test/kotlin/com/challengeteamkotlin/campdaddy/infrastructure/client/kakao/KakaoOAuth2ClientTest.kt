package com.challengeteamkotlin.campdaddy.infrastructure.client.kakao

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.application.member.exception.RetrievalFailureException
import com.challengeteamkotlin.campdaddy.fixture.auth.AuthFixture.failedAccessToken
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class KakaoOAuth2ClientTest(
    private val kakaoOAuth2Client: KakaoOAuth2Client = mockk()
) : BehaviorSpec({

    Given("getAccessToken 테스트") {
        When("getAccessToken이 틀리다면") {
            every { kakaoOAuth2Client.getAccessToken(any()) } throws RetrievalFailureException(AuthErrorCode.ACCESS_TOKEN_RETRIEVAL_FAILURE)
            Then("예외가 발생한다") {
                val getAccessTokenException = shouldThrowExactly<RetrievalFailureException> { kakaoOAuth2Client.getAccessToken(failedAccessToken) }
                val getAccessTokenExceptionErrorCode = getAccessTokenException.errorCode

                getAccessTokenExceptionErrorCode shouldBe AuthErrorCode.ACCESS_TOKEN_RETRIEVAL_FAILURE
            }
        }
    }

    Given("retrieveUserInfo 테스트") {
        When("가져온 토큰과 유저의 정보가 일치 하지 않으면") {
            every { kakaoOAuth2Client.retrieveUserInfo(failedAccessToken) } throws RetrievalFailureException(AuthErrorCode.USER_INFO_RETRIEVAL_FAILURE)
            Then("예외가 발생한다") {
                val retrieveUserInfoException = shouldThrowExactly<RetrievalFailureException> { kakaoOAuth2Client.retrieveUserInfo(failedAccessToken) }
                val retrieveUserInfoExceptionErrorCode = retrieveUserInfoException.errorCode

                retrieveUserInfoExceptionErrorCode shouldBe AuthErrorCode.USER_INFO_RETRIEVAL_FAILURE
            }
        }
    }
})
