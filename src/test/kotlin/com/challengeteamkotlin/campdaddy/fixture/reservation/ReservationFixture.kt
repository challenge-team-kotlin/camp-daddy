package com.challengeteamkotlin.campdaddy.fixture.reservation

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationsStatus
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture

object ReservationFixture {
    val wrongDateReservation = ReservationEntity(
        DateTimeFixture.tomorrow, DateTimeFixture.today, ProductFixture.tent, MemberFixture.buyer, ReservationsStatus.REQ
    )

    val reservation = ReservationEntity(
        DateTimeFixture.today, DateTimeFixture.tomorrow, ProductFixture.tent, MemberFixture.buyer, ReservationsStatus.REQ
    )
}