package com.challengeteamkotlin.campdaddy.application.review.execption

import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class ReviewErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String?
) : ErrorCode {
    REVIEW_ENTITY_NOT_FOUND(5000, HttpStatus.BAD_REQUEST,"리뷰 데이터를 찾을 수 없습니다."),
    DO_NOT_BOUGHT_BEFORE(5001,HttpStatus.BAD_REQUEST, "구매 전적이 없는 상품에 리뷰를 남길 수 없습니다."),
    ALREADY_CREATE_REVIEW(5002,HttpStatus.BAD_REQUEST,"이미 리뷰를 남긴 상품 입니다."),
    DO_NOT_HAVE_PERMISSION(5003,HttpStatus.BAD_REQUEST,"리뷰를 수정할 권한이 없습니다.")
    ;
    constructor(id: Long, status: HttpStatus) : this(id, status, null)
}
