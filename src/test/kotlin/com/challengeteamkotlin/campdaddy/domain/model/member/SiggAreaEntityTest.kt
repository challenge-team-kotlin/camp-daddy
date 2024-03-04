package com.challengeteamkotlin.campdaddy.domain.model.member

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SiggAreaEntityTest : BehaviorSpec({
    given("Sigg의 이름과 코드를 생성할 값을 줬을 떄") {
        val siggName = "test"
        val siggAdmCode = "testCode"
        val sido = SidoAreaEntity(
            name = "ddd",
            admCode = "dddd"
        )

        `when`("주어진 값을 sigg안에 넣게 되면") {
            val sigg = SiggAreaEntity(
                name = siggName,
                admCode = siggAdmCode,
                sidoAreaEntity = sido
            )
            then("주어진 값과 sigg와의 값이 같아야 한다") {
                sigg.name shouldBe siggName
                sigg.admCode shouldBe siggAdmCode
                sigg.sidoAreaEntity shouldBe sido
            }
        }
    }
})