package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberProvider

object MemberEntityFixture {
    val testPerson = MemberEntity("test@kakao.com","test", "test", "010-1234-1234", MemberProvider.KAKAO, "1234test")

}