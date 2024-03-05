package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.reservation

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ReservationJpaRepository : JpaRepository<ReservationEntity, Long> {

    fun findByProductId(productId:Long) :List<ReservationEntity>?
    fun findByMemberId(memberId:Long) :List<ReservationEntity>?

    @Query(value = "select r " +
            "from ReservationEntity as r " +
            "where r.startDate between :startDate and :endDate " +
            "or r.endDate between :startDate and :endDate " +
            "or (r.startDate <:startDate  and :endDate < r.endDate )"
    )
    fun findFirstByStartDateAndEndDate(startDate: LocalDate, endDate: LocalDate):ReservationEntity?

}