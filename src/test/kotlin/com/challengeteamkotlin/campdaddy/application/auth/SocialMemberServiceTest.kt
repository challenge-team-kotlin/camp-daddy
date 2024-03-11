package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.fixture.auth.AuthFixture.kakaoRegisterInfo
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class SocialMemberServiceTest(
    private val socialMemberService: SocialMemberService = mockk()
) : BehaviorSpec({

    Given("소셜 멤버 회원가입 테스트") {

        When("소셜 멤버의 정보가 서버에 이미 존재할 때,") {
            every { socialMemberService.registerIfAbsent(kakaoRegisterInfo) } returns MemberEntityFixture.socialExistMember
            val alreadyExistMember = socialMemberService.registerIfAbsent(kakaoRegisterInfo)

            Then("멤버 엔티티를 리턴한다.") {
                alreadyExistMember.nickname shouldBe kakaoRegisterInfo.nickname
                alreadyExistMember.email shouldBe kakaoRegisterInfo.email
            }
        }
        When("소셜 멤버의 정보가 서버에 존재하지 않을 때") {
            every { socialMemberService.registerIfAbsent(kakaoRegisterInfo) } returns MemberEntityFixture.createdMember
            val createdMember = socialMemberService.registerIfAbsent(kakaoRegisterInfo)

            Then("소셜 멤버를 저장하고 멤버 엔티티를 리턴한다.") {
                createdMember.nickname shouldBe kakaoRegisterInfo.nickname
                createdMember.email shouldBe kakaoRegisterInfo.email
            }
        }
    }
})
