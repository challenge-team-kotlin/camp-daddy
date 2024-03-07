package com.challengeteamkotlin.campdaddy.fixture.reservation

import java.time.LocalDate

object DateTimeFixture {
    val today: LocalDate = LocalDate.now()
    val tomorrow: LocalDate = LocalDate.now().plusDays(1)
}