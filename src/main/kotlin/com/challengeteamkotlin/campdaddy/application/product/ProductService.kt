package com.challengeteamkotlin.campdaddy.application.product

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductImageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.infrastructure.aws.S3Service
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.CreateProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.EditProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.CreateProductResponse
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.GetProductByMemberResponse
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class ProductService(
    private val memberRepository: MemberRepository,
    private val productRepository:ProductRepository,
    private val productImageRepository: ProductImageRepository,
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
                ProductImageEntity(this,s3Service.uploadFiles(it)) //이러면 생성되나?
            }
        }.run {
            productRepository.save(this)
        }.let {
            CreateProductResponse.from(it)
        }
    }

    @Transactional
    fun editProduct(request: EditProductRequest, memberId: Long):CreateProductRequest{
        /**
         * [토의할점 이미지 처리 어떻게 할 것인가? 동시에 처리해야하나? client가 image 업로드할때마다 해야하나?]
         * request.productId를 통해 게시물을 조회한다.
         * 해당 게시물의 작성자가 요청자인지 확인한다. 요청자가 아니면 throw Error
         * 이후 변경점들을 변경한뒤 저장한다.
         */
        val product = productRepository.findByIdOrNull(request.productId) ?: TODO("NotFoundException")

        checkAuthority(product.member.id!!, memberId)

        val createImageUrlList = request.imageUrls.toMutableList()

        request.imageUrls.forEach {
            cImageUrl ->
            product.images.forEach pImage@{
                pImageEntity ->
                if(cImageUrl == pImageEntity.imageUrl){
                    createImageUrlList.remove(cImageUrl) //성능개선 필요 현재 index가 아니라 N만큼 추가됌.
                    return@pImage
                }
            }
        }

        createImageUrlList.forEach {
            product.uploadImage(ProductImageEntity(product,it))
        }

        productRepository.save(product)
    }

    @Transactional
    fun deleteProduct(productId : Long,memberId: Long){
        /**
         * product는 소프트 델리트 되어야한다.
         * 이때 product_image에는 변경이 있어서는 안된다.
         *
         * 1. productId를 통해 Entity를 가져온다.
         * 2. 1.에서 가져온 Entity에서 MemberId와 요청한 MemberId를 비교하여 수정자가 작성자인지 확인한다.
         * 3. delete를 진행한다.
         */
        productRepository.findByIdOrNull(productId)?.run {
            checkAuthority(memberId,this.member.id!!)
            productRepository.delete(this)
        } ?: TODO("NotFoundException")
    }

    fun getProductList(){

    }

    fun getMemberProductList(memberId:Long): GetProductByMemberResponse =
        /**
         * "SELECT * FROM products WHERE member_id = ?"
         * dto로 변환.
         */
        productRepository.findByMemberId(memberId).map {
            GetProductByMemberResponse(it)
        }


    fun getCategoryList():List<String>{
        return Category.entries.map{
            it.korName
        }.toList()
    }

    private fun checkAuthority(memberId: Long, checkMemberId:Long){
        if(memberId != checkMemberId) TODO("throw Exception")
    }
}