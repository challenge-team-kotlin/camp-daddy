package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.application.auth.exception.AuthErrorCode
import com.challengeteamkotlin.campdaddy.application.auth.exception.DuplicateEmailException
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.OAuth2UserInfo
import org.springframework.stereotype.Service

@Service
class SocialMemberService(
    private val memberRepository: MemberRepository
) {

    fun registerIfAbsent(userInfo: OAuth2UserInfo): MemberEntity {
        val provider = OAuth2Provider.valueOf(userInfo.provider)
        val existMember = memberRepository.existMemberByProviderAndProviderId(provider, userInfo.providerId)
        return if (existMember) {
            // 이미 가입한 회원인 경우
            memberRepository.findMemberByProviderAndProviderId(provider, userInfo.providerId)
        } else {
            // 이메일이 이미 존재하는 경우
            val existEmail = memberRepository.existMemberByEmail(userInfo.email)
            if(existEmail) {
                throw DuplicateEmailException(AuthErrorCode.DUPLICATE_EMAIL)
            } else {
                // 이메일이 중복되지 않는 경우, 새회원
                val newMember = when (provider) {
                    OAuth2Provider.KAKAO -> MemberEntity.ofKakao(userInfo.providerId, userInfo.email, userInfo.name)
                    OAuth2Provider.NAVER -> MemberEntity.ofNaver(userInfo.providerId, userInfo.email, userInfo.name)
                    OAuth2Provider.GOOGLE -> MemberEntity.ofGoogle(userInfo.providerId, userInfo.email, userInfo.name)
                }
                memberRepository.createMember(newMember)
            }
        }
    }
}