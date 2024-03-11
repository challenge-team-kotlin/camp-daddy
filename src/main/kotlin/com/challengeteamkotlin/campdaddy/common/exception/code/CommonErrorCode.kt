package com.challengeteamkotlin.campdaddy.common.exception.code

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String?
) : ErrorCode {
    VALIDATION_FAILED(9000, HttpStatus.BAD_REQUEST),


    ;
    constructor(id: Long, status: HttpStatus) : this(id, status, null)
}
