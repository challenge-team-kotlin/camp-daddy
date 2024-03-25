package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDslSupport(
    @PersistenceContext
    private var entityManager: EntityManager
) {
    @Bean
    fun queryFactory() = JPAQueryFactory(entityManager)
}