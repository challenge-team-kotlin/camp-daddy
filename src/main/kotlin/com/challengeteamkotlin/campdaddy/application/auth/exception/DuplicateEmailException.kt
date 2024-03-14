package com.challengeteamkotlin.campdaddy.application.auth.exception

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class DuplicateEmailException(errorCode: ErrorCode) : CustomException(errorCode)