package com.challengeteamkotlin.campdaddy.application.product.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class ProductErrorCode(
    override val id : Long,
    override val status: HttpStatus,
    override val errorMessage:String
): ErrorCode{
    상품조회실패(9999,HttpStatus.BAD_REQUEST,"")
}