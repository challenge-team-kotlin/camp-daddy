package com.challengeteamkotlin.campdaddy.application.member

import com.challengeteamkotlin.campdaddy.application.member.exception.AccessDeniedException
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.fixture.auth.AuthFixture.userPrincipalForAccessDenied
import com.challengeteamkotlin.campdaddy.fixture.auth.AuthFixture.userPrincipalForDeleted
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.socialExistMember
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.socialMemberRequest
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.socialExistMemberResponse
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.socialUpdatedMember
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs

class MemberServiceTest(
    private val memberService: MemberService = mockk()
) : BehaviorSpec({

    Given("회원 ID 조회 테스트") {
        When("memberId가 존재하면") {
            every { memberService.findById(any()) } returns socialExistMemberResponse
            Then("회원의 닉네임을 가져온다") {
                socialExistMemberResponse.nickname shouldBe socialExistMember.nickname
            }
        }
        When("회원 ID 조회 테스트") {
            every { memberService.findById(99) } throws EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
            Then("예외가 던져진다") {
                val exception = shouldThrowExactly<EntityNotFoundException> { memberService.findById(99) }
                val errorCode = exception.errorCode

                errorCode shouldBe MemberErrorCode.MEMBER_NOT_FOUND
            }
        }
    }

    Given("updateProfile") {
        When("주어진 memberId가 특정 memberId와 같다면") {
            every { memberService.updateProfile(any(), any(), any()) } just runs
            Then("updateProfileRequest의 요청 변경값을 반영한다") {
                socialUpdatedMember.nickname shouldBe socialMemberRequest.nickname
            }
        }
        When("주어진 memberId가 특정 memberId와 틀리면") {
            every { memberService.updateProfile(99, socialMemberRequest, userPrincipalForAccessDenied) } throws AccessDeniedException(MemberErrorCode.ACCESS_DENIED)
            Then("예외가 던져진다") {
                val exception = shouldThrowExactly<AccessDeniedException> { memberService.updateProfile(99, socialMemberRequest, userPrincipalForAccessDenied)}
                val errorCode = exception.errorCode

                errorCode shouldBe MemberErrorCode.ACCESS_DENIED
            }
        }
    }

    Given("deleteMember") {
        When("주어진 memberId가 특정 memberId와 같다면") {
            every { memberService.deleteMember(any(), any()) } just runs
            every { memberService.findById(any()) } throws EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
            Then("soft delete로 인해 isDeleted가 true로 변경되고, 해당 memberId를 조회하면 예외가 던져진다") {
                val exception = shouldThrowExactly<EntityNotFoundException> { memberService.findById(99) }
                val errorCode = exception.errorCode

                errorCode shouldBe MemberErrorCode.MEMBER_NOT_FOUND
            }
        }
        When("주어진 memberId가 특정 memberId와 틀리면") {
            every { memberService.deleteMember(any(), any()) } throws AccessDeniedException(MemberErrorCode.ACCESS_DENIED)
            Then("예외가 던져진다") {
                val exception = shouldThrowExactly<AccessDeniedException> { memberService.deleteMember(99, userPrincipalForDeleted)}
                val errorCode = exception.errorCode

                errorCode shouldBe MemberErrorCode.ACCESS_DENIED

            }
        }
    }
})
