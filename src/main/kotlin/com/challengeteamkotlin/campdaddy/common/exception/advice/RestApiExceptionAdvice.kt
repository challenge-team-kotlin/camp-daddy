package com.challengeteamkotlin.campdaddy.common.exception.advice

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.CommonErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.dto.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestApiExceptionAdvice {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    fun handleCustomExceptionHandle(exception: CustomException): ResponseEntity<ErrorResponse> {
        logger.warn("${exception.errorCode.errorMessage}: {}", exception.message)
        return ErrorResponse.of(exception.errorCode)
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.warn("Validation Failed: {}", exception.message)
        val errorCode = CommonErrorCode.VALIDATION_FAILED
        return ErrorResponse.of(errorCode, exception.bindingResult)
    }
}
