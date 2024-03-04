package com.challengeteamkotlin.campdaddy.domain.model.member

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegionEntityTest : BehaviorSpec({
    given("Region을 생성할 값을 줬을 때") {
        val regionMemberId = 123412312314
        val regionSidoAreaEntity = SidoAreaEntity (
            name = "test",
            admCode = "test"
        )
        val regionSiggAreaEntity = SiggAreaEntity (
            name = "test",
            admCode = "test",
            sidoAreaEntity = regionSidoAreaEntity
        )
        `when`("주어진 값을 Region 안에 넣게 되면") {
            val region = RegionEntity (
                memberId = regionMemberId,
                siggAreaEntity = regionSiggAreaEntity
            )
            then("주어진 값과 Region의 값이 같아야한다") {
                region.memberId shouldBe  regionMemberId
                region.siggAreaEntity shouldBe  regionSiggAreaEntity
            }
        }
    }
})