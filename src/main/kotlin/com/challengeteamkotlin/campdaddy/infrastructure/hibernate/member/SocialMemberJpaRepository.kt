package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.domain.model.member.SocialMemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SocialMemberJpaRepository : JpaRepository<SocialMemberEntity, Long> {
    fun existsByEmailAndProviderAndProviderId(email: String, provider: OAuth2Provider, providerId: String): Boolean
    fun existsSocialMemberEntity(socialMemberEntity: SocialMemberEntity): Boolean
}