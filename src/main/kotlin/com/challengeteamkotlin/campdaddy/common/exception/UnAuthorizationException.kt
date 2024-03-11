package com.challengeteamkotlin.campdaddy.common.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class UnAuthorizationException (
    override val errorCode:ErrorCode
):CustomException(errorCode)