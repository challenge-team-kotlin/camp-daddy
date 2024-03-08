package com.challengeteamkotlin.campdaddy.application.product

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductImageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindAllByAvailableReservationDto
import com.challengeteamkotlin.campdaddy.infrastructure.aws.S3Service
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.CreateProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.EditProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.SearchProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.GetProductByMemberResponse
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.ProductResponse
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.SearchProductResponse
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(
    private val memberRepository: MemberRepository,
    private val productRepository:ProductRepository,
    private val productImageRepository: ProductImageRepository,
) {

    init {

        val member1 = MemberEntity("buyer@google.com", "buyer", "백다삼", "01012345678")
        val member2 = MemberEntity("seller@google.com", "seller", "김다팜", "01087654321")
        memberRepository.save(member1)
        memberRepository.save(member2)

        val lamp = ProductEntity(member1, 20000, "불이 잘 들어오는 램프", "잘 들어 옵니다.", Category.LAMPS)
        val tent = ProductEntity(member1, 100_000, "텐트 빌려드려요", "텐트 빌려드림ㅇㅇ", Category.TENTS)
        productRepository.save(lamp)
        productRepository.save(tent)

    }
    @Transactional
    fun createProduct(request: CreateProductRequest, memberId:Long): ProductResponse {
        /**
         * memberId 로부터 MemberEntity 조회.
         * ProductEntity 생성.
         * Response반환.
         */
        val userInfo = memberRepository.findByIdOrNull(memberId) ?: TODO("throw EntityNotFound")

        return request.from(userInfo).apply{
            request.images.map {
                ProductImageEntity(this,it) //이러면 생성되나?
            }
        }.run {
            productRepository.save(this)
        }.let {
            ProductResponse.from(it)
        }
    }

    @Transactional
    fun editProduct(request: EditProductRequest, memberId: Long): ProductResponse {
        val product = productRepository.findByIdOrNull(request.productId) ?: TODO("NotFoundException")

        checkAuthority(product.member.id!!, memberId)

        val createImageUrlList = request.imageUrls.toMutableList()
        val deleteImageUrlList = product.images.toMutableList()

        request.imageUrls.forEach cImage@{
            cImageUrl ->
            product.images.forEach pImage@{
                pImageEntity ->
                if(cImageUrl == pImageEntity.imageUrl){
                    createImageUrlList.remove(cImageUrl)
                    deleteImageUrlList.remove(pImageEntity)
                    return@cImage
                }
            }
        }

        createImageUrlList.forEach {
            product.uploadImage(ProductImageEntity(product,it))
        }

        deleteImageUrlList.map {
            product.deleteImage(it)
        }

        productRepository.save(product)

        return ProductResponse.from(product)
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

    @Transactional
    fun getProductDetail(productId:Long):ProductResponse =
        productRepository.findByIdOrNull(productId)?.let {
            ProductResponse.from(it)
        } ?: TODO("NotFoundException")


    @Transactional
    fun getProductList(request: SearchProductRequest,pageable: Pageable):Page<List<FindAllByAvailableReservationDto> >{
        //페이지 객체 생성.
        return productRepository.findAllByAvailableReservation(request.startDate,request.endDate,pageable)
    }

    @Transactional
    fun getMemberProductList(memberId:Long): List<GetProductByMemberResponse> =
        productRepository.findByMemberId(memberId).map {
            GetProductByMemberResponse.from(it)
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
