package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.SiggAreaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SiggAreaJpaRepository : JpaRepository<SiggAreaEntity, Long> {
}