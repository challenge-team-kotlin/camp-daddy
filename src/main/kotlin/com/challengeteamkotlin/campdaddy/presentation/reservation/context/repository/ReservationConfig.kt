package com.challengeteamkotlin.campdaddy.presentation.reservation.context.repository

import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.reservation.ReservationJpaRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.reservation.ReservationRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReservationConfig {

    @Bean
    fun reservationRepository(jpaRepository: ReservationJpaRepository): ReservationRepository {
        return ReservationRepositoryImpl(jpaRepository)
    }

}