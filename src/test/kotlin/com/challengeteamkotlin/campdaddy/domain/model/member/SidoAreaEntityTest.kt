package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.fixture.sido.SidoFixture.sidoTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SidoAreaEntityTest : BehaviorSpec({
    Given("sido 생성 테스트") {
        val sido = sidoTest
        When("sido 를 생성하면") {
            Then("sido 가 생성된다") {
                sido.name shouldBe "sido"
                sido.admCode shouldBe "1234"
            }
        }
    }
})