package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun existsByEmailOrNickname(email: String, nickname: String): Boolean
    fun findByEmail(email: String): MemberEntity?

}