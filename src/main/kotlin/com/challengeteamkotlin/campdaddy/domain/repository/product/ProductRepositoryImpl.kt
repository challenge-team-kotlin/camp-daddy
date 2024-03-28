package com.challengeteamkotlin.campdaddy.domain.repository.product

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationDto
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationFilterDto
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationDto
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationFilterDto
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product.ProductJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository
):ProductRepository{
    override fun create(productEntity: ProductEntity): ProductEntity {
        return productJpaRepository.save(productEntity)
    }

    override fun delete(productEntity: ProductEntity) {
        return productJpaRepository.delete(productEntity)
    }

    override fun getProductById(productId: Long): ProductEntity? {
        return productJpaRepository.findByIdOrNull(productId)
    }

    override fun getProductByMemberId(memberId: Long): List<ProductEntity> {
        return  productJpaRepository.findByMemberId(memberId)
    }

    override fun getProductByReservation(
        startDate: LocalDate,
        endDate: LocalDate,
        category: Category,
        pageable: Pageable
    ): Slice<FindByReservationDto> {
        return productJpaRepository.findByReservation(
            startDate,
            endDate,
            category,
            pageable
        )
    }

    override fun getProductByReservationFilter(
        startDate: LocalDate,
        endDate: LocalDate,
        category: Category,
        pageable: Pageable
    ): Slice<FindByReservationFilterDto> {
        return productJpaRepository.findByReservationFilter(
            startDate,
            endDate,
            category,
            pageable
        )
    }

    override fun getProductBySearchableAndReservation(
        startDate: LocalDate,
        endDate: LocalDate,
        category: Category,
        search: String,
        pageable: Pageable
    ): Slice<FindBySearchableAndReservationDto> {
        return productJpaRepository.findBySearchableAndReservation(
            startDate,
            endDate,
            category,
            search,
            pageable
        )
    }

    override fun getProductBySearchableAndReservationFilter(
        startDate: LocalDate,
        endDate: LocalDate,
        category: Category,
        search: String,
        pageable: Pageable
    ): Slice<FindBySearchableAndReservationFilterDto> {
        return productJpaRepository.findBySearchableAndReservationFilter(
            startDate,
            endDate,
            category,
            search,
            pageable
        )
    }

    override fun getProductAll(pageable: Pageable): Slice<ProductEntity> {
        return productJpaRepository.findAll(pageable)
    }
}