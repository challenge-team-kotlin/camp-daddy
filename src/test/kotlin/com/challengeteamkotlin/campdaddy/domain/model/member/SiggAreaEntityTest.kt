package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.fixture.sido.SidoFixture.sidoTest
import com.challengeteamkotlin.campdaddy.fixture.sigg.SiggFixture.siggTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SiggAreaEntityTest : BehaviorSpec({
    Given("sigg 생성 테스트") {
        val sigg = siggTest
        When("sigg 를 생성하면") {
            Then("sigg 가 생성된다") {
                sigg.name shouldBe "sigg"
                sigg.admCode shouldBe "1234"
                sigg.sidoAreaEntity shouldBe sidoTest
            }
        }
    }

})