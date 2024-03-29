package com.challengeteamkotlin.campdaddy.application.member

import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.common.auth.isAuthorized
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun findById(memberId: Long): MemberResponse {
        return MemberResponse.from(getProfile(memberId))
    }

    @Transactional
    fun updateProfile(memberId: Long, request: UpdateProfileRequest, userPrincipal: UserPrincipal) {
        isAuthorized(memberId, userPrincipal) {
            val profile = getProfile(memberId)
            profile.toUpdate(request)
            memberRepository.updateMember(profile)
        }
    }

    @Transactional
    fun deleteMember(memberId: Long, userPrincipal: UserPrincipal) {
        isAuthorized(memberId, userPrincipal) {
            val member = getProfile(memberId)
            memberRepository.deleteMember(member)
        }
    }

    private fun getProfile(memberId: Long): MemberEntity {
        return memberRepository.getMemberByIdOrNull(memberId)
            ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
    }
}
