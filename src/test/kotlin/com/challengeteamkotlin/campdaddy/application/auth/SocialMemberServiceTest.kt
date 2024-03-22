package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtPlugin
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith

//@ExtendWith(MockKExtension::class)
//class SocialMemberServiceTest(
//): DescribeSpec({
//    extensions(SpringExtension)
//
//    afterContainer {
//        clearAllMocks()
//    }
//
//    val memberRepository = mockk<MemberRepository>()
//    val jwtPlugin = mockk<JwtPlugin>()
//    val socialMemberService = SocialMemberService(memberRepository, jwtPlugin)
//
//
//
//    describe("소셜 멤버 회원가입 테스트") {
//
////        context("소셜 멤버의 Email이 이미 존재하면") {
////            every { memberRepository.existMemberByEmail(existEmailMember.email) } returns true
////            it("예외가 던져진다.") {
////             shouldThrow<DuplicateEmailException> {
////                 socialMemberService.registerIfAbsent(newAuthInfo)
////             }
////            }
////        }
//
////        context("소셜 멤버의 정보가 서버에 존재하지 않는다면") {
////            every { memberRepository.existMemberByEmail(any()) } returns false
////            every { memberRepository.existMemberByProviderAndProviderId(any(), any()) } returns false
////            every { memberRepository.createMember(any()) } returns kakaoNewMember
////            val saveMember = socialMemberService.registerIfAbsent(oAuth2KakaoUserInfo)
////
////            it("회원 가입이 진행된다") {
////                verify(exactly = 1) { memberRepository.createMember(any()) }
////                verify(exactly = 0) { memberRepository.findMemberByProviderAndProviderId(any(), any()) }
////                saveMember.id shouldBe kakaoNewMember.id
////                saveMember.email shouldBe kakaoNewMember.email
////                saveMember.name shouldBe kakaoNewMember.name
////                saveMember.provider shouldBe kakaoNewMember.provider
////            }
////        }
////        context("소셜 멤버의 정보가 서버에 존재하면") {
////            every { memberRepository.existMemberByProviderAndProviderId(any(), any()) } returns true
////            every { memberRepository.findMemberByProviderAndProviderId(any(), any()) } returns existMember
////            val existSocialMember = memberRepository.findMemberByProviderAndProviderId(OAuth2Provider.valueOf(existAuthInfo.provider), existAuthInfo.id)
////            it("소셜 멤버의 정보를 반환한다") {
////                verify(exactly = 0) { memberRepository.createMember(any()) }
////                verify(exactly = 1) { memberRepository.findMemberByProviderAndProviderId(any(), any()) }
////                existSocialMember.id shouldBe existMember.id
////                existSocialMember.email shouldBe existMember.email
////                existSocialMember.name shouldBe existMember.name
////                existSocialMember.provider shouldBe existMember.provider
////            }
////        }
////    }
//})
