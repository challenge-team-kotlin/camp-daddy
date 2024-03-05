package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.RegionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RegionJpaRepository: JpaRepository<RegionEntity, Long> {
}