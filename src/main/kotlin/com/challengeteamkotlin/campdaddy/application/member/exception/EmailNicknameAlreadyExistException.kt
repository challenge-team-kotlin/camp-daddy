package com.challengeteamkotlin.campdaddy.application.member.exception

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class EmailNicknameAlreadyExistException(errorCode: ErrorCode) : CustomException(errorCode)