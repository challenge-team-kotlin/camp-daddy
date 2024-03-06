package com.challengeteamkotlin.campdaddy.presentation.reservation

import com.challengeteamkotlin.campdaddy.application.reservation.ReservationService
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust.CreateReservationRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust.PatchReservationStatusRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.response.ReservationResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservations")
class ReservationController(
    private val reservationService: ReservationService
) {

    @PostMapping
    fun createReservation(
        @RequestBody @Valid createReservationRequest: CreateReservationRequest
    ): ResponseEntity<Unit> {
        // TODO JWT 멤버 아이디
        createReservationRequest.memberId = 1L

        return reservationService.createReservation(createReservationRequest)
            .let {
                ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build()
            }
    }

    @PatchMapping("/{reservationId}")
    fun patchReservationStatus(
        @PathVariable reservationId: Long,
        @RequestBody patchReservationStatusRequest: PatchReservationStatusRequest
    ): ResponseEntity<Unit> {
        patchReservationStatusRequest.reservationId = reservationId
        return reservationService.patchReservationStatus(patchReservationStatusRequest)
            .run {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .build()
            }
    }

    @GetMapping("/products/{productId}")
    fun getProductReservations(
        @PathVariable productId: Long
    ): ResponseEntity<List<ReservationResponse>> {
        return reservationService.getProductReservations(productId)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(it)
            }
    }

    @GetMapping("/members/{memberId}")
    fun getMemberReservations(
        @PathVariable memberId: Long
    ): ResponseEntity<List<ReservationResponse>> {
        return reservationService.getMemberReservations(memberId)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(it)
            }
    }

    @GetMapping("/{reservationId}")
    fun getReservation(
        @PathVariable reservationId: Long
    ): ResponseEntity<ReservationResponse> {
        return reservationService.getReservation(reservationId)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(it)
            }
    }
}