package com.challengeteamkotlin.campdaddy.domain.model.member

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberEntityTest : BehaviorSpec({

    given("멤버를 생성할 값을 주어졌을 때") {
        val email = "test@mail.com"
        val nickname = "nk_test"
        val name = "test"
        val phoneNumber = "010-1234-1234"
        `when`("멤버 안에 그 값을 넣게 되면") {
            val member = MemberEntity(
                email = email,
                nickname = nickname,
                name = name,
                phoneNumber = phoneNumber
            )
            then("주어진 값과 멤버 안의 값이 같아야 한다") {
                member.email shouldBe email
                member.nickname shouldBe nickname
                member.name shouldBe name
                member.phoneNumber shouldBe phoneNumber
            }
        }
    }
})