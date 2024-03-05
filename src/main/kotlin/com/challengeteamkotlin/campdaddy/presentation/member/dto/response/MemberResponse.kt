package com.challengeteamkotlin.campdaddy.presentation.member.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity

data class MemberResponse(
    val id: Long,
    val email: String,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val role: String,
) {
    companion object {
        fun from(member: MemberEntity): MemberResponse {
            return MemberResponse(
                id = member.id!!,
                email = member.email,
                name = member.name,
                nickname = member.nickname,
                phoneNumber = member.phoneNumber,
                role = member.role.name
            )
        }
    }
}
