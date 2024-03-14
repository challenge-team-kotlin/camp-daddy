package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun existsByProviderAndProviderId(provider: OAuth2Provider, providerId: String): Boolean
    fun findByProviderAndProviderId(provider: OAuth2Provider, providerId: String): MemberEntity

    fun existsByEmail(email: String): Boolean

}