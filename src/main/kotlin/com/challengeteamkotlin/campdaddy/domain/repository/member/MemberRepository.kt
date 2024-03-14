package com.challengeteamkotlin.campdaddy.domain.repository.member

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider


interface MemberRepository {

    fun getMemberByIdOrNull(memberId: Long): MemberEntity?
    fun updateMember(memberEntity: MemberEntity): MemberEntity

    fun deleteMember(memberEntity: MemberEntity)

    fun existMemberByEmail(email: String): Boolean

    fun existMemberByProviderAndProviderId(provider: OAuth2Provider, providerId: String): Boolean

    fun findMemberByProviderAndProviderId(provider: OAuth2Provider, providerId: String): MemberEntity

    fun createMember(memberEntity: MemberEntity): MemberEntity
}