package com.challengeteamkotlin.campdaddy.presentation.member

import com.challengeteamkotlin.campdaddy.application.member.MemberService
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.presentation.member.dto.request.UpdateProfileRequest
import com.challengeteamkotlin.campdaddy.presentation.member.dto.response.MemberResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class MemberController(
    private val memberService: MemberService
) {
    @Operation(summary = "프로필 조회")
    @GetMapping("/members/{memberId}")
    fun getProfile(
        @PathVariable memberId: Long
    ): ResponseEntity<MemberResponse> {
        val response = memberService.findById(memberId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

    @Operation(summary = "프로필 수정")
    @PutMapping("/members/{memberId}")
    fun updateProfile(
        @PathVariable memberId: Long,
        @RequestBody request: UpdateProfileRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        memberService.updateProfile(memberId, request, userPrincipal)
        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/members/{memberId}")
    fun deleteMember(
        @PathVariable memberId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Any> {
        memberService.deleteMember(memberId, userPrincipal)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("회원탈퇴 완료했습니다.")
    }
}