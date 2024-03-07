package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberProvider


object MemberFixture {
    val buyer = MemberEntity("buyer@google.com", "buyer", "백다삼", "01012345678", MemberProvider.KAKAO, "1234test")
    val seller = MemberEntity("seller@google.com", "seller", "김다팜", "01087654321", MemberProvider.KAKAO, "1234test")
}