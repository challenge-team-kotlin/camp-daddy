package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.reservation

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ReservationRepositoryImpl(
    private val reservationJpaRepository: ReservationJpaRepository
) : ReservationRepository {

    override fun createReservation(reservationEntity: ReservationEntity): ReservationEntity {
        return reservationJpaRepository.save(reservationEntity)
    }

    override fun getReservationByIdOrNull(reservationId: Long): ReservationEntity? {
        return reservationJpaRepository.findByIdOrNull(reservationId)
    }

    override fun getReservationsProductId(productId: Long, pageable: Pageable): Page<ReservationEntity> {
        return reservationJpaRepository.findByProductId(productId, pageable)
    }

    override fun getReservationByMemberId(memberId: Long, pageable: Pageable): Page<ReservationEntity> {
        return reservationJpaRepository.findByMemberId(memberId, pageable)
    }

    override fun isExistsReservation(productId: Long, memberId: Long, reservationStatus: ReservationStatus): Boolean {
        return reservationJpaRepository.existsByProductIdAndMemberIdAndReservationStatus(
            productId,
            memberId,
            reservationStatus
        )
    }

    override fun getFirstReservation(productId: Long, startDate: LocalDate, endDate: LocalDate): ReservationEntity? {
        return reservationJpaRepository.findFirstByProductIdAndStartDateAndEndDate(productId, startDate, endDate)
    }
}