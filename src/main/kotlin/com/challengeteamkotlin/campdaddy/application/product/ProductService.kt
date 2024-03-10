package com.challengeteamkotlin.campdaddy.application.product

import com.challengeteamkotlin.campdaddy.application.product.exception.ProductErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductImageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.CreateProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.EditProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.SearchProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.GetProductByMemberResponse
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.ProductResponse
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ProductService(
    private val memberRepository: MemberRepository,
    private val productRepository:ProductRepository,
) {

    @Transactional
    fun createProduct(request: CreateProductRequest, memberId:Long): ProductResponse {
        /**
         * memberId 로부터 MemberEntity 조회.
         * ProductEntity 생성.
         * Response반환.
         */
        val userInfo = memberRepository.findByIdOrNull(memberId) ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

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
        val product = productRepository.findByIdOrNull(request.productId) ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

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
        productRepository.findByIdOrNull(productId)?.run {
            checkAuthority(memberId,this.member.id!!)
            productRepository.delete(this)
        } ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)
    }

    @Transactional
    fun getProductDetail(productId:Long):ProductResponse =
        productRepository.findByIdOrNull(productId)?.let {
            ProductResponse.from(it)
        } ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)


    @Transactional
    fun getProductList(request: SearchProductRequest,pageable: Pageable):Slice<*> {
        if(request.search != null) {
            return if(request.filterReservation) productRepository.findBySearchableAndReservationFilter(
                        startDate = request.startDate,
                        endDate = request.endDate,
                        category = request.category,
                        search = request.search,
                        pageable = pageable,
                ) else productRepository.findBySearchableAndReservation(
                    startDate = request.startDate,
                    endDate = request.endDate,
                    category = request.category,
                    search = request.search,
                    pageable = pageable,
                )

        }else if(request.filterReservation){
            //검색어가 제공되지않고 모든 상품을 조회할때.
            return productRepository.findByReservationFilter(
                    startDate = request.startDate,
                    endDate = request.endDate,
                    category = request.category,
                    pageable = pageable
            )
        }else{
            //검색어가 제공되지않고 모든 상품을 볼때
            return productRepository.findByReservation(
                    startDate = request.startDate,
                    endDate = request.endDate,
                    category = request.category,
                    pageable = pageable
            )
        }
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
