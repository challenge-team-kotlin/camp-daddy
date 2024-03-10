package com.challengeteamkotlin.campdaddy.application.chat.exception

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class ChatFailureException(errorCode: ErrorCode): CustomException(errorCode){
}