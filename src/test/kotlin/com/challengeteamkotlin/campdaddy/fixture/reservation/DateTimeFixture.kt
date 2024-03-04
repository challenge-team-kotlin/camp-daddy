package com.challengeteamkotlin.campdaddy.fixture.reservation

import java.time.LocalDateTime

object DateTimeFixture {
    val today = LocalDateTime.now()
    val tomorrow = LocalDateTime.now().plusDays(1)
}