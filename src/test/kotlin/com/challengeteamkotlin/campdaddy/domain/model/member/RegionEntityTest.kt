package com.challengeteamkotlin.campdaddy.domain.model.member

import com.challengeteamkotlin.campdaddy.fixture.region.RegionFixture.regionTest
import com.challengeteamkotlin.campdaddy.fixture.sigg.SiggFixture.siggTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class RegionEntityTest : BehaviorSpec({
    Given("region 생성 테스트") {
        val region = regionTest
        When("region 을 생성하면") {
            Then("region 이 생성된다") {
                region.memberId shouldBe 123412341234
                region.siggAreaEntity shouldBe siggTest
            }
        }
    }

})