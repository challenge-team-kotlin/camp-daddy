package com.challengeteamkotlin.campdaddy.application.auth

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberProvider
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.response.KakaoUserInfoResponse
import org.springframework.stereotype.Service

@Service
class SocialMemberService(
    private val memberRepository: MemberRepository
) {

    fun registerIfAbsent(userInfo: KakaoUserInfoResponse): MemberEntity {
        return if (!memberRepository.existsByProviderAndProviderId(MemberProvider.KAKAO, userInfo.id.toString())) {
            val socialMember = MemberEntity.ofKakao(userInfo.id, userInfo.nickname, userInfo.email, userInfo.phoneNumber, userInfo.name)
            memberRepository.save(socialMember)
        } else {
            memberRepository.findByProviderAndProviderId(MemberProvider.KAKAO, userInfo.id.toString())
        }
    }
}