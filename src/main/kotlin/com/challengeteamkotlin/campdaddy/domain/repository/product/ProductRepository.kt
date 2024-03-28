package com.challengeteamkotlin.campdaddy.domain.repository.product

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationDto


import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationFilterDto
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationDto
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationFilterDto
import org.springframework.data.domain.Pageable

import org.springframework.data.domain.Slice
import java.time.LocalDate

interface ProductRepository {

    fun create(productEntity: ProductEntity):ProductEntity;

    fun delete(productEntity: ProductEntity);

    fun getProductAll(pageable: Pageable):Slice<ProductEntity>

    fun getProductById(productId:Long):ProductEntity?

    fun getProductByMemberId(memberId:Long):List<ProductEntity>

    fun getProductByReservation(startDate: LocalDate, endDate: LocalDate, category: Category, pageable: Pageable):Slice<FindByReservationDto>
    fun getProductByReservationFilter(startDate: LocalDate, endDate: LocalDate, category: Category, pageable: Pageable): Slice<FindByReservationFilterDto>

    fun getProductBySearchableAndReservation(startDate: LocalDate, endDate: LocalDate, category: Category, search: String, pageable: Pageable): Slice<FindBySearchableAndReservationDto>

    fun getProductBySearchableAndReservationFilter(startDate: LocalDate, endDate: LocalDate, category: Category, search: String, pageable: Pageable): Slice<FindBySearchableAndReservationFilterDto>

}
