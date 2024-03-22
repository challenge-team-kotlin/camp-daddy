package com.challengeteamkotlin.campdaddy.presentation.auth.dto

import com.challengeteamkotlin.campdaddy.application.auth.SocialMemberService
import com.challengeteamkotlin.campdaddy.presentation.auth.dto.request.OAuth2SignupRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuth2Controller(
    private val socialMemberService: SocialMemberService
){

    @PostMapping("/signup")
    fun signup(
      @Valid @RequestBody signupRequest: OAuth2SignupRequest
    ): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(socialMemberService.register(signupRequest))
    }
}