package com.challengeteamkotlin.campdaddy.infrastructure.aws

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.challengeteamkotlin.campdaddy.common.exception.code.CommonErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.code.ErrorCode
import com.challengeteamkotlin.campdaddy.infrastructure.aws.exception.AwsException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class S3Service (
    private val amazonS3Client: AmazonS3Client
) {
    private val bucket: String = "camp-daddy"

    fun uploadFiles(file: MultipartFile): String {
        val fileName: String = "camp/"+UUID.randomUUID().toString() + "_" + file.originalFilename

        checkExtensionName(fileName)

        try {
            //upload 유효성 검사.
            ObjectMetadata().apply {
                contentLength = file.size
                contentType = file.contentType
            }.let {
                amazonS3Client.putObject(bucket, fileName, file.inputStream, it)
                amazonS3Client.getUrl(bucket, fileName).toString()
            }
        } finally {
            throw AwsException(CommonErrorCode.AWS_IMAGE_UPLOAD_FAIL)
        }
    }
    //client imageFile 이

    fun deleteFile(fileName:String):Unit =
        amazonS3Client.deleteObject(DeleteObjectRequest(bucket,fileName))


    private fun checkExtensionName(originalFileName:String) =
        originalFileName.split(".").let {
            check(it.contains("jpg") or it.contains("jpeg") or it.contains("png")){
                throw AwsException(CommonErrorCode.AWS_IMAGE_NAME_FAIL)
            }
        }
}