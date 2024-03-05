package com.challengeteamkotlin.campdaddy.application.member.exception

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class EmailPasswordMisMatchException(errorCode: ErrorCode) : CustomException(errorCode)