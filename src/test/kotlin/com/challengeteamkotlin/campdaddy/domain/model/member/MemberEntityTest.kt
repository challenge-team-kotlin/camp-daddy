package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.testPerson
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.wrongEmailMembers
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.wrongNameMembers
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.wrongNicknameMembers
import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.wrongPhoneNumMembers
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldNotMatch

class MemberEntityTest : BehaviorSpec({

    Given("멤버 생성 테스트") {
        val member = testPerson

        val emailRegex = Regex("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
        val nicknameRegex = Regex("^\\S{2,10}$")
        val nameRegex = Regex("^\\S{2,10}$")
        val phoneNumRegex = Regex("^010-\\d{4}-\\d{4}$")

        When("멤버의 이메일 형식이 틀리면") {
            Then("새로운 멤버를 만들 수 없다") {
                wrongEmailMembers.map { it.email shouldNotMatch emailRegex.toString() }
            }
        }

        When("멤버의 닉네임 형식이 틀리면") {
            Then("새로운 멤버를 만들 수 없다") {
                wrongNicknameMembers.map { it.email shouldNotMatch nicknameRegex.toString() }
            }
        }

        When("멤버의 이름 형식이 틀리면") {
            Then("새로운 멤버를 만들 수 없다") {
                wrongNameMembers.map { it.email shouldNotMatch nameRegex.toString() }
            }
        }

        When("멤버의 휴대폰 번호 형식이 틀리면") {
            Then("새로운 멤버를 만들 수 없다") {
                wrongPhoneNumMembers.map { it.email shouldNotMatch phoneNumRegex.toString() }
            }
        }

        When("멤버의 모든 형식이 통과하면") {
            Then("새로운 멤버를 만들 수 있다") {
                member.email matches emailRegex
                member.nickname matches nicknameRegex
                member.name matches nameRegex
                member.phoneNumber matches phoneNumRegex
            }
        }
    }

})