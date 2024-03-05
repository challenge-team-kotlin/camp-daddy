package com.challengeteamkotlin.campdaddy.application.reservation.exception

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class StartDateLessThanEndDateException(errorCode: ErrorCode) : CustomException(errorCode) {
}