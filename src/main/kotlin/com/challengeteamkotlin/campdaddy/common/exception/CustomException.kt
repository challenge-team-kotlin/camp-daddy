package com.challengeteamkotlin.campdaddy.common.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

open class CustomException(
    open val errorCode: ErrorCode
) : RuntimeException()
