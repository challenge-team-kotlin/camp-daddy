package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider

object MemberFixture {
//    val testPerson = MemberEntity("test@kakao.com", "test", OAuth2Provider.KAKAO, "1234test")
    val buyer = MemberEntity("buyer@google.com", OAuth2Provider.KAKAO, "1234test", "buyer", "buyer")
    val seller = MemberEntity("seller@google.com", OAuth2Provider.KAKAO, "1234test", "seller", "seller")

    const val memberId = 1L
    const val wrongMemberId = 999L
}