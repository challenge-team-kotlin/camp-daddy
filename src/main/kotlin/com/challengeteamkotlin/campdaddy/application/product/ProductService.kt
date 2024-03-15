package com.challengeteamkotlin.campdaddy.application.product

import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.application.product.exception.ProductErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.common.exception.UnAuthorizationException
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductImageEntity
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
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

@Service
class ProductService(
        private val memberRepository: MemberRepository,
        private val productRepository: ProductRepository,
) {

    @Transactional
    fun createProduct(request: CreateProductRequest, memberId: Long): ProductResponse {
        val userInfo = memberRepository.getMemberByIdOrNull(memberId)
                ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

        return request.from(userInfo).apply {
            request.images.map {
                this.uploadImage(ProductImageEntity(this, it))
            }
        }.run {
            productRepository.create(this)
        }.let {
            ProductResponse.from(it)
        }
    }

    @Transactional
    fun editProduct(request: EditProductRequest, productId: Long, memberId: Long): ProductResponse {
        val member = getMember(memberId)
        val product = getProduct(productId)

        checkAuthority(product.member.id!!, member.id!!)

        product.category = Category.valueOf(request.category)
        product.title = request.title
        product.content = request.content
        product.pricePerDay = request.price

        product.images
                .filterNot { request.imageUrls.contains(it.imageUrl) }
                .forEach { product.deleteImage(it) }

        request.imageUrls
                .filterNot { product.images.map { images -> images.imageUrl }.contains(it) }
                .forEach { product.uploadImage(ProductImageEntity(product, it)) }

        productRepository.create(product)

        return ProductResponse.from(product)
    }

    @Transactional
    fun deleteProduct(productId: Long, memberId: Long) {
        val member = getMember(memberId)
        val product = getProduct(productId)

        checkAuthority(member.id!!, product.member.id!!)

        productRepository.delete(product)
    }

    @Transactional
    fun getProductDetail(productId: Long): ProductResponse =
            productRepository.getProductById(productId)?.let {
                ProductResponse.from(it)
            } ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)


    @Transactional
    fun getProductList(request: SearchProductRequest, pageable: Pageable): Slice<*> {
        if (request.search != null) {
            return if (request.filterReservation) productRepository.getProductBySearchableAndReservationFilter(
                    startDate = request.startDate,
                    endDate = request.endDate,
                    category = request.category,
                    search = request.search,
                    pageable = pageable,
            ) else productRepository.getProductBySearchableAndReservation(
                    startDate = request.startDate,
                    endDate = request.endDate,
                    category = request.category,
                    search = request.search,
                    pageable = pageable,
            )

        } else if (request.filterReservation) {
            //검색어가 제공되지않고 모든 상품을 조회할때.
            return productRepository.getProductByReservationFilter(
                    startDate = request.startDate,
                    endDate = request.endDate,
                    category = request.category,
                    pageable = pageable
            )
        } else {
            //검색어가 제공되지않고 모든 상품을 볼때
            return productRepository.getProductByReservation(
                    startDate = request.startDate,
                    endDate = request.endDate,
                    category = request.category,
                    pageable = pageable
            )
        }
    }

    @Transactional
    fun getMemberProductList(memberId: Long): List<GetProductByMemberResponse> =
            productRepository.getProductByMemberId(memberId).map {
                GetProductByMemberResponse.from(it)
            }


    fun getCategoryList(): List<String> {
        return Category.entries.map {
            it.korName
        }.toList()
    }

    private fun getProduct(productId: Long): ProductEntity =
            productRepository.getProductById(productId)
                    ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

    private fun getMember(memberId: Long): MemberEntity =
            memberRepository.getMemberByIdOrNull(memberId) ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)

    private fun checkAuthority(memberId: Long, checkMemberId: Long) {
        if (memberId != checkMemberId) throw UnAuthorizationException(ProductErrorCode.ACCESS_DENIED)
    }
}
