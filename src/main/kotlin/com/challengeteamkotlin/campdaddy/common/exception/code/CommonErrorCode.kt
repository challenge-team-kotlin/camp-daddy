package com.challengeteamkotlin.campdaddy.common.exception.code

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val id: Long,
    override val status: HttpStatus,
    override val errorMessage: String?
) : ErrorCode {
    VALIDATION_FAILED(9000, HttpStatus.BAD_REQUEST),
    AWS_IMAGE_UPLOAD_FAIL(9001,HttpStatus.BAD_REQUEST,"이미지 업로드에 실패했습니다."),
    AWS_IMAGE_NAME_FAIL(9002,HttpStatus.BAD_REQUEST,"지원하지 않는 확장자 입니다."),
    ;
    constructor(id: Long, status: HttpStatus) : this(id, status, null)
}
