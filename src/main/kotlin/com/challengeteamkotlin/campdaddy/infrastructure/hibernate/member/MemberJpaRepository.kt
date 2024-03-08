package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberProvider
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun existsByProviderAndProviderId(kakao: MemberProvider, toString: String): Boolean
    fun findByProviderAndProviderId(kakao: MemberProvider, toString: String): MemberEntity

}