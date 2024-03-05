package com.challengeteamkotlin.campdaddy.presentation.member.dto.request

import jakarta.validation.constraints.Pattern

data class SignupRequest(

    @field: Pattern(
        regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$",
        message = "이메일의 형식에 맞게 입력해주세요"
    )
    val email: String,

    @field: Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{4,15}\$",
        message = "영문, 숫자, 특수문자를 포함한 4~15자리로 입력해주세요"
    )
    val password: String,

    @field: Pattern(
        regexp = "^\\S{2,10}$",
        message = "2~10자리로 입력해주세요"
    )
    val name: String,

    @field: Pattern(
        regexp = "^\\S{2,10}$",
        message = "2~10자리로 입력해주세요"
    )
    val nickname: String,

    @field: Pattern(
        regexp = "^010-\\d{4}-\\d{4}$",
        message = "올바른 핸드폰 번호를 입력해 주세요 ex) 010-1234-1234"
    )
    val phoneNumber: String,
)
