package com.challengeteamkotlin.campdaddy.common.exception.code

import org.springframework.http.HttpStatus

interface ErrorCode {
    val id: Long
    val status: HttpStatus
    val errorMessage: String?
}
