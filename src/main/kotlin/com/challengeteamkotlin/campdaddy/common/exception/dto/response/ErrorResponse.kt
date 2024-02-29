package com.challengeteamkotlin.campdaddy.common.exception.dto.response

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult

data class ErrorResponse(
    val errorId: Long,
    val payload: Any? = null
) {

    companion object {

        fun of(errorCode: ErrorCode): ResponseEntity<ErrorResponse> =
            ResponseEntity.status(errorCode.status).body(makeResponse(errorCode))

        fun of(errorCode: ErrorCode, bindingResult: BindingResult): ResponseEntity<ErrorResponse> =
            ResponseEntity.status(errorCode.status).body(makeResponse(errorCode, bindingResult))

        private fun makeResponse(errorCode: ErrorCode) = ErrorResponse(errorCode.id, errorCode.errorMessage)

        private fun makeResponse(errorCode: ErrorCode, bindingResult: BindingResult): ErrorResponse {
            val errorMap = hashMapOf<String, String>()

            bindingResult.fieldErrors.map {
                errorMap[it.rejectedValue] to it.defaultMessage
            }

            return ErrorResponse(errorCode.id, errorMap)
        }
    }

}
