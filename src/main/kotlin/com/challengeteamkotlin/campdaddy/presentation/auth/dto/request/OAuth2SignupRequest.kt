package com.challengeteamkotlin.campdaddy.presentation.auth.dto.request

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.OAuth2Provider
import jakarta.validation.constraints.Pattern

data class OAuth2SignupRequest(
    val email: String,
    val provider: String,
    val providerId: String,
    val name: String,
    @field: Pattern(
        regexp = "^\\S{2,10}$",
        message = "닉네임은 최소 2글자, 최대 10글자 입력할 수 있습니다."
    )
    val nickname: String,
) {
        fun to(): MemberEntity {
            return MemberEntity(
                email = email,
                provider = OAuth2Provider.valueOf(provider),
                providerId = providerId,
                name = name,
                nickname = nickname
            )
        }
}
