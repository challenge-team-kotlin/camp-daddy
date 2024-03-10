package com.challengeteamkotlin.campdaddy.fixture.reservation

import java.time.LocalDate
import java.time.LocalDateTime

object DateTimeFixture {
    val today: LocalDate = LocalDate.now()
    val tomorrow: LocalDate = LocalDate.now().plusDays(1)
}