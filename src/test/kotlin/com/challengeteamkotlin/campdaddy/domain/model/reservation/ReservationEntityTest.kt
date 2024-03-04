package com.challengeteamkotlin.campdaddy.domain.model.reservation

import com.challengeteamkotlin.campdaddy.fixture.reservation.ReservationFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldBeBefore

class ReservationEntityTest : BehaviorSpec({
    Given("예약 Entity 생성 테스트") {
        When("예약 Entity의 시작 날짜가 종료 날짜보다 뒤 일 경우") {
            val wrongDateReservation = ReservationFixture.wrongDateReservation
            Then("새로운 예약 Entity를 만들 수 없다.") {
                wrongDateReservation.startDate shouldBeAfter wrongDateReservation.endDate
            }

        }
        When("예약 Entity의 시작 날짜가 종료 날짜보다 앞 일 경우") {
            val reservation = ReservationFixture.reservation
            Then("새로운 예약 Entity를 만들 수 있다.") {
                reservation.startDate shouldBeBefore reservation.endDate
            }
        }
    }
})