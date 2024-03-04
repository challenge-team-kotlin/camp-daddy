package com.challengeteamkotlin.campdaddy.fixture.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity

object MemberEntityFixture {
    val testPerson = MemberEntity("test@mail.com","test", "test", "010-1234-1234")

    private val wrongEmailPerson1 = MemberEntity("@mail.com","test", "test", "010-1234-1234")
    private val wrongEmailPerson2 = MemberEntity("test@ail","test", "test", "010-1234-1234")
    private val wrongEmailPerson3 = MemberEntity("testmail.com","test", "test", "010-1234-1234")
    private val wrongEmailPerson4 = MemberEntity("test#mail.com","test", "test", "010-1234-1234")

    val wrongEmailMembers = listOf(wrongEmailPerson1, wrongEmailPerson2, wrongEmailPerson3, wrongEmailPerson4)

    private val wrongNicknamePerson1 = MemberEntity("test@mail.com","1", "test", "010-1234-1234")
    private val wrongNicknamePerson2 = MemberEntity("test@mail.com","1 2", "test", "010-1234-1234")
    private val wrongNicknamePerson3 = MemberEntity("test@mail.com","12345678901", "test", "010-1234-1234")
    private val wrongNicknamePerson4 = MemberEntity("test@mail.com","", "test", "010-1234-1234")

    val wrongNicknameMembers = listOf(wrongNicknamePerson1, wrongNicknamePerson2, wrongNicknamePerson3, wrongNicknamePerson4)

    private val wrongNamePerson1 = MemberEntity("test@mail.com","test", "1", "010-1234-1234")
    private val wrongNamePerson2 = MemberEntity("test@mail.com","test", "1 2", "010-1234-1234")
    private val wrongNamePerson3 = MemberEntity("test@mail.com","test", "12345678901", "010-1234-1234")
    private val wrongNamePerson4 = MemberEntity("test@mail.com","test", "", "010-1234-1234")

    val wrongNameMembers = listOf(wrongNamePerson1, wrongNamePerson2, wrongNamePerson3, wrongNamePerson4)

    private val wrongPhoneNumPerson1 = MemberEntity("test@mail.com","test", "test", "")
    private val wrongPhoneNumPerson2 = MemberEntity("test@mail.com","test", "test", "016-1234-1234")
    private val wrongPhoneNumPerson3 = MemberEntity("test@mail.com","test", "test", "010-12-1234")
    private val wrongPhoneNumPerson4 = MemberEntity("test@mail.com","test", "test", "010-1234-12")

    val wrongPhoneNumMembers = listOf(wrongPhoneNumPerson1, wrongPhoneNumPerson2, wrongPhoneNumPerson3, wrongPhoneNumPerson4)
}