package com.challengeteamkotlin.campdaddy.application.review.execption

import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode

class ChangeReviewRefusedException(errorCode: ErrorCode) : CustomException(errorCode) {
}