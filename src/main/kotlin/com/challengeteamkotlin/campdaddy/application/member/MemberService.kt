package com.challengeteamkotlin.campdaddy.application.member

import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.common.exception.code.CommonErrorCode
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {
    fun getProfile(memberId: Long): MemberResponse {
        return memberRepository.findByIdOrNull(memberId)?.let { MemberResponse.from(it) }
            ?: throw EntityNotFoundException(CommonErrorCode.VALIDATION_FAILED)
    }

    fun updateProfile(memberId: Long, request: UpdateProfileRequest) {
        val profile = memberRepository.findByIdOrNull(memberId) ?: throw EntityNotFoundException(CommonErrorCode.VALIDATION_FAILED)
        profile.toUpdate(request)
        memberRepository.save(profile)
    }

    fun deleteMember(memberId: Long) {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw EntityNotFoundException(CommonErrorCode.VALIDATION_FAILED)
        memberRepository.delete(member)
    }

}