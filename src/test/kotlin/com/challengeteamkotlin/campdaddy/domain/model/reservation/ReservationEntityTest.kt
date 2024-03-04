package com.challengeteamkotlin.campdaddy.domain.model.reservation

import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture
import com.challengeteamkotlin.campdaddy.fixture.reservation.DateTimeFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ReservationEntityTest : BehaviorSpec({
    Given("예약 생성 테스트") {
        When("예약 Entity를 정상적으로 생성하면") {
            val reservation = ReservationEntity(
                    DateTimeFixture.today, DateTimeFixture.tomorrow, ProductFixture.tent, MemberFixture.buyer, ReservationsStatus.REQ
            )
            Then("Entity에 값이 저장된다.") {
                reservation.startDate shouldBe DateTimeFixture.today
                reservation.endDate shouldBe DateTimeFixture.tomorrow
                reservation.productEntity shouldBe ProductFixture.tent
                reservation.memberEntity shouldBe MemberFixture.buyer
                reservation.reservationsStatus shouldBe ReservationsStatus.REQ
            }
        }
    }
})