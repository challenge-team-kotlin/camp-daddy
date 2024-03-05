package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.reservation

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {
}