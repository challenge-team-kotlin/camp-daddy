package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.testPerson
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberEntityTest : BehaviorSpec({

    Given("멤버 생성 테스트") {
        val member = testPerson
        When("멤버의 이메일이 형식이 맞으면") {
            val emailRegex = Regex("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
            Then("새로운 멤버가 만들어진다") {
                member.email matches emailRegex
            }
        When("멤버의 닉네임이 형식에 맞으면") {
            val nicknameRegex = Regex("^\\S{2,10}$")
            Then("새로운 멤버가 만들어진다") {
                member.nickname matches nicknameRegex
            }
        }
        When("멤버의 이름이 형식에 맞으면") {
            val nameRegex = Regex("^\\S{2,10}$")
            Then("새로운 멤버가 만들어진다") {
                member.name matches nameRegex
            }
        }
        When("멤버의 휴대폰 번호가 형식에 맞으면") {
            val phoneNumRegex = Regex("^010-\\d{4}-\\d{4}$")
            Then("새로운 멤버가 만들어진다") {
                member.phoneNumber matches phoneNumRegex
            }
        }
        }
    }

})