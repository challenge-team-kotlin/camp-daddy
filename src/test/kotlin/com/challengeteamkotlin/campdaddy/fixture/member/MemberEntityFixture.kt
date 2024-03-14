package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse

object MemberEntityFixture {
    // Request
    val socialMemberRequest = UpdateProfileRequest("이승상승상")

    // Entity
    val testPerson = MemberEntity("test@kakao.com", "이승상", OAuth2Provider.KAKAO, "1234")
    val existEmailMember = MemberEntity("test@test.com", "test", OAuth2Provider.KAKAO, "1234")
    val socialExistMember = MemberEntity("seungsang@kakao.com", "이승상", OAuth2Provider.KAKAO, "1234")
    val existMember = MemberEntity("exist@mail.com", "exist", OAuth2Provider.KAKAO, "1234")
    val kakaoNewMember = MemberEntity("test@kakao.com", "kakao", OAuth2Provider.KAKAO, "1234")
    val socialUpdatedMember = MemberEntity("seungsang@kakao.com", "이승상승상", OAuth2Provider.KAKAO, "1234")
    // Response
    val socialExistMemberResponse = MemberResponse(1, socialExistMember.email, socialExistMember.name, socialExistMember.nickname)
}