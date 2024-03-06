package com.challengeteamkotlin.campdaddy.application.product

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.infrastructure.aws.S3Service
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.CreateProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.CreateProductResponse
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val memberRepository: MemberRepository,
    private val productRepository:ProductRepository,
    private val s3Service: S3Service
) {

    @Transactional
    fun createProduct(request: CreateProductRequest, memberId:Long): CreateProductResponse {
        /**
         * memberId 로부터 MemberEntity 조회.
         * ProductEntity 생성.
         * Response반환.
         */
        val userInfo = memberRepository.findByIdOrNull(memberId) ?: TODO("throw EntityNotFound")

        return request.from(userInfo).apply{
            request.images.map {
                ProductImageEntity(this,s3Service.uploadFiles(it))
            }
        }.run {
            productRepository.save(this)
        }.let {
            CreateProductResponse.from(it)
        }
    }

    @Transactional
    fun editProduct(request:editProductRequest,memberId: Long){
        /**
         * [토의할점 이미지 처리 어떻게 할 것인가? 동시에 처리해야하나? client가 image 업로드할때마다 해야하나?]
         */
    }

    @Transactional
    fun deleteProduct(productId : Long,memberId: Long){
        /**
         * product는 소프트 델리트 되어야한다.
         * 이때 product_image에는 변경이 있어서는 안된다.
         */
    }

    fun getProductList(){

    }

    fun getAvailableDate(){

    }

    fun getMemberProductList(memberId:Long){
        val test = productRepository.findByMemberId(memberId)
    }

    fun getCategoryList():List<String>{
        return Category.entries.map{
            it.korName
        }.toList()
    }
}