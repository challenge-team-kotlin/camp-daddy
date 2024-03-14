package com.challengeteamkotlin.campdaddy.domain.repository.reservation

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface ReservationRepository {
    fun createReservation(reservationEntity: ReservationEntity): ReservationEntity
    fun getReservationByIdOrNull(reservationId: Long): ReservationEntity?
    fun getReservationsProductId(productId: Long, pageable: Pageable): Page<ReservationEntity>
    fun getReservationByMemberId(memberId: Long, pageable: Pageable): Page<ReservationEntity>
    fun isExistsReservation(productId: Long, memberId: Long, reservationStatus: ReservationStatus): Boolean
    fun getFirstReservation(productId: Long, startDate: LocalDate, endDate: LocalDate): ReservationEntity?
}