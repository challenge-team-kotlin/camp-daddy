package com.challengeteamkotlin.campdaddy.application.member

import com.challengeteamkotlin.campdaddy.application.member.exception.EmailNicknameAlreadyExistException
import com.challengeteamkotlin.campdaddy.application.member.exception.EmailPasswordMisMatchException
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.common.exception.code.CommonErrorCode
import com.challengeteamkotlin.campdaddy.common.security.jwt.JwtPlugin
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberRole
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.member.MemberJpaRepository
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.LoginRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.SignupRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.LoginResponse
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberJpaRepository: MemberJpaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    fun getProfile(memberId: Long): MemberResponse {
        return memberJpaRepository.findByIdOrNull(memberId)?.let { MemberResponse.from(it) }
            ?: throw EntityNotFoundException(CommonErrorCode.VALIDATION_FAILED)
    }

    fun updateProfile(memberId: Long, request: UpdateProfileRequest) {
        val profile = memberJpaRepository.findByIdOrNull(memberId) ?: throw EntityNotFoundException(CommonErrorCode.VALIDATION_FAILED)
        profile.toUpdate(request)
        memberJpaRepository.save(profile)
    }

    fun signup(memberRole: MemberRole, request: SignupRequest) {
        checkedEmailOrNicknameExists(request.email, request.nickname, memberJpaRepository)
        memberJpaRepository.save(
            MemberEntity(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                nickname = request.nickname,
                phoneNumber = request.phoneNumber,
                role = memberRole
            )
        )
    }

    fun login(request: LoginRequest): LoginResponse {
        val member = memberJpaRepository.findByEmail(request.email) ?: throw EntityNotFoundException(CommonErrorCode.VALIDATION_FAILED)
        checkedLoginPassword(member.password, request.password, passwordEncoder)
        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = member.id.toString(),
                email = member.email,
                role = member.role.name
            )
        )
    }

    fun deleteMember(memberId: Long) {
        val member = memberJpaRepository.findByIdOrNull(memberId) ?: throw EntityNotFoundException(CommonErrorCode.VALIDATION_FAILED)
        memberJpaRepository.delete(member)
    }

   private fun checkedLoginPassword(password: String, inputPassword: String, passwordEncoder: PasswordEncoder) {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw EmailPasswordMisMatchException(MemberErrorCode.EMAIL_PASSWORD_MISMATCH)
        }
    }

   private fun checkedEmailOrNicknameExists(email: String, nickname: String, memberJpaRepository: MemberJpaRepository) {
        if (memberJpaRepository.existsByEmailOrNickname(email, nickname)) {
            throw EmailNicknameAlreadyExistException(MemberErrorCode.EMAIL_NICKNAME_ALREADY_EXIST)
        }
    }
}