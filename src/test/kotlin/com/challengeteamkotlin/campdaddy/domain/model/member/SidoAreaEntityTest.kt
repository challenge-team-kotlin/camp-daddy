package com.challengeteamkotlin.campdaddy.domain.model.member

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SidoAreaEntityTest : BehaviorSpec({
    given("시도의 이름과 코드를 생성 할 값을 줬을 때") {
        val name = "sido_test"
        val admCode = "test_code"
        `when`("그 값을 시도 안에 넣게 되면") {
            val sido = SidoAreaEntity (
                name = name,
                admCode = admCode
                )
            then("주어진 값과 시도 안의 값이 같아야 한다") {
                sido.name shouldBe name
                sido.admCode shouldBe admCode
            }
        }
    }
})