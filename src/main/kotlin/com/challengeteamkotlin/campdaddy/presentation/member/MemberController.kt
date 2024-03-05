package com.challengeteamkotlin.campdaddy.presentation.member

import com.challengeteamkotlin.campdaddy.application.member.MemberService
import com.challengeteamkotlin.campdaddy.application.member.exception.AccessDeniedException
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.common.security.jwt.UserPrincipal
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberRole
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.LoginRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.SignupRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.LoginResponse
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @Operation(summary = "프로필 조회")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/members/{memberId}")
    fun getProfile(
        @PathVariable memberId: Long
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.getProfile(memberId))
    }

    @Operation(summary = "프로필 수정")
    @PutMapping("/members/{memberId}")
    fun updateProfile(
        @PathVariable memberId: Long,
        @RequestBody request: UpdateProfileRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        if (memberId == userPrincipal.id) {
            memberService.updateProfile(memberId, request)
        } else {
            throw AccessDeniedException(MemberErrorCode.ACCESS_DENIED)
        }
        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signup(
        @RequestParam memberRole: MemberRole,
        @Valid @RequestBody request: SignupRequest
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.signup(memberRole, request))
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.login(request))
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/members/{memberId}")
    fun deleteMember(
        @PathVariable memberId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Any> {
        if (memberId == userPrincipal.id) {
            memberService.deleteMember(memberId)
        } else {
            throw AccessDeniedException(MemberErrorCode.ACCESS_DENIED)
        }
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("회원탈퇴 완료했습니다.")
    }
}