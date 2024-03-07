package com.challengeteamkotlin.campdaddy.fixture.reservation

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture

object ReservationFixture {
    val wrongDateReservation = ReservationEntity(
        DateTimeFixture.tomorrow,
        DateTimeFixture.today,
        10000,
        ProductFixture.tent,
        MemberFixture.buyer,
        ReservationStatus.REQ
    )

    val reservation = ReservationEntity(
        DateTimeFixture.today,
        DateTimeFixture.tomorrow,
        10000,
        ProductFixture.tent,
        MemberFixture.buyer,
        ReservationStatus.REQ
    )
}