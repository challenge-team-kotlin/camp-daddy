package com.challengeteamkotlin.campdaddy.application.member

import com.challengeteamkotlin.campdaddy.application.member.exception.EmailNicknameAlreadyExistException
import com.challengeteamkotlin.campdaddy.application.member.exception.EmailPasswordMisMatchException
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.common.exception.code.CommonErrorCode
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
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

   private fun checkedLoginPassword(password: String, inputPassword: String, passwordEncoder: PasswordEncoder) {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw EmailPasswordMisMatchException(MemberErrorCode.EMAIL_PASSWORD_MISMATCH)
        }
    }

   private fun checkedEmailOrNicknameExists(email: String, nickname: String, memberRepository:MemberRepository) {
        if (memberRepository.existsByEmailOrNickname(email, nickname)) {
            throw EmailNicknameAlreadyExistException(MemberErrorCode.EMAIL_NICKNAME_ALREADY_EXIST)
        }
    }
}