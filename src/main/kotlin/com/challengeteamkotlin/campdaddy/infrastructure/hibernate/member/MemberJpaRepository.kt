package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {

}
