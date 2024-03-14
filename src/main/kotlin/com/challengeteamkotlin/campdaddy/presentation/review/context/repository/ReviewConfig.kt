package com.challengeteamkotlin.campdaddy.presentation.review.context.repository

import com.challengeteamkotlin.campdaddy.domain.repository.review.ReviewRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.review.ReviewJpaRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.review.ReviewRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReviewConfig {

    @Bean
    fun reviewRepository(reviewJpaRepository: ReviewJpaRepository): ReviewRepository {
        return ReviewRepositoryImpl(reviewJpaRepository)
    }

}