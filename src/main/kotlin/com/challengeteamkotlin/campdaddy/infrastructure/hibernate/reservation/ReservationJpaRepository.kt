package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.reservation

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {

    fun existsByProductIdAndMemberIdAndReservationStatus(
        productId: Long, memberId: Long, reservationStatus: ReservationStatus
    ): Boolean

    fun findByProductId(productId: Long, pageable: Pageable): Page<ReservationEntity>

    fun findByMemberId(memberId: Long, pageable: Pageable): Page<ReservationEntity>

    @Query(
        value = """
        select r 
            from ReservationEntity as r 
            where r.product.id = :productId
            and r.reservationStatus not in (
                    com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus.REQ,
                    com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus.CANCELED
            )
            and r.startDate between :startDate and :endDate
            or r.endDate between :startDate and :endDate 
            or (r.startDate <:startDate  and :endDate < r.endDate )
        """
    )
    fun findFirstByProductIdAndStartDateAndEndDate(
        productId: Long, startDate: LocalDate, endDate: LocalDate
    ): ReservationEntity?

}