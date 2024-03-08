package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberProvider
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse

object MemberEntityFixture {
    // Request
    val socialMemberRequest = UpdateProfileRequest("이승상승상")

    // Entity
    val testPerson = MemberEntity("test@kakao.com", "이승상", "이승상", "010-1234-1234", MemberProvider.KAKAO, "1234")
    val createdMember = MemberEntity("seungsang@kakao.com", "이승상", "이승상", "010-1234-5678", MemberProvider.KAKAO, "1234")
    val socialExistMember = MemberEntity("seungsang@kakao.com", "이승상", "승상승", "010-1234-1234", MemberProvider.KAKAO, "1234")
    val socialUpdatedMember = MemberEntity("seungsang@kakao.com", "이승상승상", "승상승", "010-1234-1234", MemberProvider.KAKAO, "1234")
    // Response
    val socialExistMemberResponse = MemberResponse(1, socialExistMember.email, socialExistMember.name, socialExistMember.nickname)
}