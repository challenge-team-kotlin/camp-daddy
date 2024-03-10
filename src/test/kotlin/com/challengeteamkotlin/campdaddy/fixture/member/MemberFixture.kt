package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberProvider

object MemberFixture {
    val testPerson = MemberEntity("test@kakao.com", "test", "test", "010-1234-1234", MemberProvider.KAKAO, "1234test")
    val buyer = MemberEntity("buyer@google.com", "buyer", "백다삼", "01012345678", MemberProvider.KAKAO, "1234test")
    val seller = MemberEntity("seller@google.com", "seller", "김다팜", "01087654321", MemberProvider.KAKAO, "1234test")

    const val memberId = 1L
    const val wrongMemberId = 999L
}