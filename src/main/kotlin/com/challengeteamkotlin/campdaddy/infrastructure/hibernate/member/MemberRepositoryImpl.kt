package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import org.springframework.data.repository.findByIdOrNull

class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository
): MemberRepository {
    override fun getMemberByIdOrNull(memberId: Long): MemberEntity? {
        return memberJpaRepository.findByIdOrNull(memberId)
    }

    override fun updateMember(memberEntity: MemberEntity): MemberEntity {
        return memberJpaRepository.save(memberEntity)
    }

    override fun deleteMember(memberEntity: MemberEntity) {
        return memberJpaRepository.delete(memberEntity)
    }

    override fun existMemberByEmail(email: String): Boolean {
        return memberJpaRepository.existsByEmail(email)
    }

    override fun existMemberByProviderAndProviderId(provider: OAuth2Provider, providerId: String): Boolean {
        return memberJpaRepository.existsByProviderAndProviderId(provider, providerId)
    }

    override fun findMemberByProviderAndProviderId(provider: OAuth2Provider, providerId: String): MemberEntity {
        return memberJpaRepository.findByProviderAndProviderId(provider, providerId)
    }

    override fun createMember(memberEntity: MemberEntity): MemberEntity {
        return memberJpaRepository.save(memberEntity)
    }
}