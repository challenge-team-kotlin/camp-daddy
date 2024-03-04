package com.challengeteamkotlin.campdaddy.fixture.reservation

import java.time.LocalDateTime

object DateTimeFixture {
    val today: LocalDateTime = LocalDateTime.now()
    val tomorrow: LocalDateTime = LocalDateTime.now().plusDays(1)
}