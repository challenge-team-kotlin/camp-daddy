package com.challengeteamkotlin.campdaddy.fixture.reservation

import java.time.LocalDate

object DateFixture {
    val today: LocalDate = LocalDate.now()
    val tomorrow: LocalDate = LocalDate.now().plusDays(1)
}