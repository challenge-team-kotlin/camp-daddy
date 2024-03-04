package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity


object MemberFixture {
    val buyer = MemberEntity("buyer@google.com", "buyer", "백다삼", "01012345678")
    val seller = MemberEntity("seller@google.com", "seller", "김다팜", "01087654321")
}