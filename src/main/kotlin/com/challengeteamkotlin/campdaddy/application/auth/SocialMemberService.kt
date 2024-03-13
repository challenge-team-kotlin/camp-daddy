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
        val existEmail = memberRepository.existsByEmail(userInfo.email)
        if (existEmail) throw DuplicateEmailException(AuthErrorCode.DUPLICATE_EMAIL)
        return if (memberRepository.existsByProviderAndProviderId(provider, userInfo.id)) {
            memberRepository.findByProviderAndProviderId(provider, userInfo.id)
        } else {
            memberRepository.save(
                when (provider) {
                    OAuth2Provider.KAKAO -> {
                        MemberEntity.ofKakao(
                            id = userInfo.id,
                            email = userInfo.email,
                            name = userInfo.name
                        )
                    }

                    OAuth2Provider.NAVER -> {
                        MemberEntity.ofNaver(
                            id = userInfo.id,
                            email = userInfo.email,
                            name = userInfo.name
                        )
                    }

                    OAuth2Provider.GOOGLE -> {
                        MemberEntity.ofGoogle(
                            id = userInfo.id,
                            email = userInfo.email,
                            name = userInfo.name
                        )
                    }
                }
            )
        }
    }
}