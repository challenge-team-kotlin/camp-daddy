package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.fixture.member.MemberEntityFixture.testPerson
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe


class MemberEntityTest : BehaviorSpec({

    Given("유저 생성 확인") {
        When("생성 된 유저가 있다면") {
            Then("생성 된 유저와 등록 된 값은 같다") {
                testPerson.email shouldBe "test@kakao.com"
                testPerson.name shouldBe "이승상"
                testPerson.provider shouldBe OAuth2Provider.KAKAO
                testPerson.providerId shouldBe "1234"

            }
        }
    }
})