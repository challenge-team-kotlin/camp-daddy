package com.challengeteamkotlin.campdaddy.common.exception.code

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String?
) : ErrorCode {
    VALIDATION_FAILED(9000, HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(9001, HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    ID_NOT_FOUND(9002, HttpStatus.BAD_REQUEST, "조회 된 회원이 없습니다."),
    AWS_IMAGE_UPLOAD_FAIL(9003,HttpStatus.BAD_REQUEST,"이미지 업로드에 실패했습니다."),
    AWS_IMAGE_NAME_FAIL(9004,HttpStatus.BAD_REQUEST,"지원하지 않는 확장자 입니다."),
    ;
    constructor(id: Long, status: HttpStatus) : this(id, status, null)
}
