package com.challengeteamkotlin.campdaddy.application.reservation.exception

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class EndDateLessThanStartDateException(errorCode: ErrorCode) : CustomException(errorCode) {
}