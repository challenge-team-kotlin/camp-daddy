package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberProvider
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun existsByEmailOrNickname(email: String, nickname: String): Boolean
    fun findByEmail(email: String): MemberEntity?

    fun existsByProviderAndProviderId(kakao: MemberProvider, toString: String): Boolean
    fun findByProviderAndProviderId(kakao: MemberProvider, toString: String): MemberEntity

}