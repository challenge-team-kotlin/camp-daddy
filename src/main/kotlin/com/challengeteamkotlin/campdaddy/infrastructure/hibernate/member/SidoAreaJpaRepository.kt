package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.SidoAreaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SidoAreaJpaRepository: JpaRepository<SidoAreaEntity, Long> {
}