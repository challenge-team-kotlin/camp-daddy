package com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.fasterxml.jackson.annotation.JsonIgnore

data class ReservationPatchStatusRequest(
    val reservationStatus: ReservationStatus
) {
    @JsonIgnore
    var reservationId: Long? = null

    @JsonIgnore
    var memberId: Long? = null

}
