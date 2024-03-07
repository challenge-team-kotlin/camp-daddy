package com.challengeteamkotlin.campdaddy.application.product.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class ProductErrorCode(
    override val id : Long,
    override val status: HttpStatus,
    override val errorMessage:String
): ErrorCode{
    PRODUCT_NOT_FOUND_EXCEPTION(3001, HttpStatus.BAD_REQUEST, "상품이 조회되지 않습니다."),

    ;
}