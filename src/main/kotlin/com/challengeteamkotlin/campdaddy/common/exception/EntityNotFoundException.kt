package com.challengeteamkotlin.campdaddy.common.exception

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class EntityNotFoundException(
    override val errorCode: ErrorCode
) : CustomException(errorCode)
