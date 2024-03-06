package com.challengeteamkotlin.campdaddy.infrastructure.aws.exception

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import kotlin.reflect.KClass

class AwsException(errorCode: ErrorCode):CustomException(errorCode)
