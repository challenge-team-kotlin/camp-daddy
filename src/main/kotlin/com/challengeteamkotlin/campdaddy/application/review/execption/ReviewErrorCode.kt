package com.challengeteamkotlin.campdaddy.application.review.execption

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class ReviewErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String?
) : ErrorCode {
    REVIEW_ENTITY_NOT_FOUND(5000, HttpStatus.BAD_REQUEST,"리뷰 데이터를 찾을 수 없습니다."),
    ;
    constructor(id: Long, status: HttpStatus) : this(id, status, null)
}
