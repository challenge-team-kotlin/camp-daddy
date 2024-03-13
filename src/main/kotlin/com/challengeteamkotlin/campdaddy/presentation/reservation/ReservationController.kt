package com.challengeteamkotlin.campdaddy.presentation.reservation

import com.challengeteamkotlin.campdaddy.application.reservation.ReservationService
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust.CreateReservationRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.response.ReservationResponse
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservations")
class ReservationController(
    private val reservationService: ReservationService
) {

    @PostMapping
    fun createReservation(
        @RequestBody @Valid createReservationRequest: CreateReservationRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {

        return reservationService.createReservation(userPrincipal.id, createReservationRequest)
            .let {
                ResponseEntity
                    .status(HttpStatus.CREATED)
                    .build()
            }
    }

    @PatchMapping("/{reservationId}")
    fun patchReservationStatus(
        @PathVariable reservationId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody reservationStatus: ReservationStatus
    ): ResponseEntity<Unit> {
        return reservationService.patchReservationStatus(reservationId, userPrincipal.id, reservationStatus)
            .run {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .build()
            }
    }

    @GetMapping("/products/{productId}")
    fun getProductReservations(
        @PathVariable productId: Long,
        @RequestParam pageNo: Int
    ): ResponseEntity<Page<ReservationResponse>> {
        return reservationService.getProductReservations(productId, pageNo, 10)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(it)
            }
    }

    @GetMapping("/me")
    fun getMemberReservations(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestParam pageNo: Int
    ): ResponseEntity<Page<ReservationResponse>> {
        return reservationService.getMemberReservations(userPrincipal.id, pageNo, 10)
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