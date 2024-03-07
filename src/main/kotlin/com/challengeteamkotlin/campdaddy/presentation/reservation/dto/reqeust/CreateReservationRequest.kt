package com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.fasterxml.jackson.annotation.JsonIgnore
import org.jetbrains.annotations.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class CreateReservationRequest(
    @NotNull
    val productId: Long,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val startDate: LocalDate,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val endDate: LocalDate,
) {
    @JsonIgnore
    var memberId: Long? = null
    fun toEntity(productEntity: ProductEntity, memberEntity: MemberEntity, totalPrice: Long): ReservationEntity {
        return ReservationEntity(
            startDate = startDate,
            endDate = endDate,
            product = productEntity,
            member = memberEntity,
            reservationStatus = ReservationStatus.REQ,
            totalPrice = totalPrice
        )
    }
}