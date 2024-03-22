package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.application.auth.exception.DuplicateEmailException
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberRole
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtPlugin
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.request.OAuth2SignupRequest
import org.springframework.stereotype.Service

@Service
class SocialMemberService(
    private val memberRepository: MemberRepository,
    private val jwtPlugin: JwtPlugin
) {

    fun register(signupRequest: OAuth2SignupRequest): String {
        val existEmail = memberRepository.existMemberByEmail(signupRequest.email)
        if (existEmail) throw DuplicateEmailException(AuthErrorCode.DUPLICATE_EMAIL)
        return memberRepository.createMember(signupRequest.to())
            .let {
                jwtPlugin.generateAccessToken(
                    subject = it.id!!.toString(),
                    email = it.email,
                    role = MemberRole.MEMBER.name
                )
            }
    }

    fun existMember(provider: OAuth2Provider, providerId: String): Boolean {
        return memberRepository.existMemberByProviderAndProviderId(provider, providerId)
    }

    fun findMember(provider: OAuth2Provider, providerId: String): MemberEntity? {
        return memberRepository.findMemberByProviderAndProviderId(provider, providerId)
    }
}