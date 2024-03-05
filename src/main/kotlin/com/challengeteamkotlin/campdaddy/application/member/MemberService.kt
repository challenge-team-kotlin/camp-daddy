package com.challengeteamkotlin.campdaddy.application.member

import com.challengeteamkotlin.campdaddy.common.security.jwt.JwtPlugin
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberRole
import com.challengeteamkotlin.campdaddy.domain.model.member.checkedEmailOrNicknameExists
import com.challengeteamkotlin.campdaddy.domain.model.member.checkedLoginPassword
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
            ?: TODO("Exception")
    }

    fun updateProfile(memberId: Long, request: UpdateProfileRequest) {
        val profile = memberJpaRepository.findByIdOrNull(memberId) ?: TODO("Exception")
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
        val member = memberJpaRepository.findByEmail(request.email) ?: TODO("Exception")
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
        val member = memberJpaRepository.findByIdOrNull(memberId) ?: TODO("Exception")
        memberJpaRepository.delete(member)
    }
}