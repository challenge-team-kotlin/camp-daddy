package com.challengeteamkotlin.campdaddy.domain.repository.product

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationDto
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product.ProductJpaRepository
import org.springframework.data.jpa.repository.Query

import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationFilterDto
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationDto
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationFilterDto
import org.springframework.data.domain.Pageable

import org.springframework.data.domain.Slice
import java.time.LocalDate

interface ProductRepository : ProductJpaRepository {

    fun findByMemberId(memberId: Long): List<ProductEntity>

    @Query("""
        SELECT
            new com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationFilterDto(
                p.id,
                p.pricePerDay,
                p.title,
                p.content,
                MIN(pi.imageUrl)
            )
        FROM
            ProductEntity p
        LEFT JOIN
            ReservationEntity r
                ON p.id = r.product.id
                AND ((r.startDate >= :startDate AND r.startDate <= :endDate)
                    OR (r.endDate >= :startDate AND r.endDate <= :endDate))
        LEFT JOIN
            ProductImageEntity pi
                ON p.id = pi.product.id
        WHERE
            r.id IS NULL
            AND p.category = :category
        GROUP BY
            p.id,
            p.pricePerDay,
            p.title,
            p.content
    """)
    fun findByReservationFilter(startDate: LocalDate, endDate: LocalDate, category: Category, pageable: Pageable): Slice<FindByReservationFilterDto>

    @Query("""
        SELECT 
            new com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindByReservationDto(
            p.id,
            p.pricePerDay,
            p.title,
            p.content,
            MIN(pi.imageUrl),
            SUM(CASE WHEN r.id IS NOT NULL THEN 1 ELSE 0 END)
            )
        FROM 
            ProductEntity p 
        LEFT JOIN 
            ReservationEntity r 
            ON p.id = r.product.id 
            AND (r.startDate BETWEEN :startDate AND :endDate 
                OR r.endDate BETWEEN :startDate AND :endDate) 
        LEFT JOIN 
            ProductImageEntity pi
            ON p.id = pi.product.id
        WHERE 
            p.category = :category 
        GROUP BY 
            p.id, 
            p.pricePerDay, 
            p.title, 
            p.content
    """)
    fun findByReservation(startDate: LocalDate, endDate: LocalDate, category: Category, pageable: Pageable): Slice<FindByReservationDto>

    @Query("""
                SELECT 
            new com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationDto(
            p.id,
            p.pricePerDay,
            p.title,
            p.content,
            MIN(pi.imageUrl),
            SUM(CASE WHEN r.id IS NOT NULL THEN 1 ELSE 0 END)
            )
        FROM 
            ProductEntity p 
        LEFT JOIN 
            ReservationEntity r 
            ON p.id = r.product.id 
            AND (r.startDate BETWEEN :startDate AND :endDate 
                OR r.endDate BETWEEN :startDate AND :endDate) 
        LEFT JOIN 
            ProductImageEntity pi
            ON p.id = pi.product.id
        WHERE 
            p.category = :category 
            AND p.title like %:search%
        GROUP BY 
            p.id, 
            p.pricePerDay, 
            p.title, 
            p.content
    """
    )
    fun findBySearchableAndReservation(startDate: LocalDate, endDate: LocalDate, category: Category, search: String, pageable: Pageable): Slice<FindBySearchableAndReservationDto>


    @Query("""
        SELECT
            new com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindBySearchableAndReservationFilterDto(
                p.id,
                p.pricePerDay,
                p.title,
                p.content,
                min (p_i.imageUrl)
            )
        FROM ProductEntity as p
            LEFT JOIN ReservationEntity r
                ON p.id = r.product.id
                    AND  (r.startDate between :startDate and :endDate
                        OR r.endDate between :startDate and :endDate)
            LEFT JOIN
                ProductImageEntity p_i
                ON p.id = p_i.product.id
        WHERE
            r.id IS null
            AND p.category = :category
            AND p.title like %:search%
        GROUP BY p.id, p.pricePerDay, p.title, p.content
        """
    )
    fun findBySearchableAndReservationFilter(startDate: LocalDate, endDate: LocalDate, category: Category, search: String, pageable: Pageable): Slice<FindBySearchableAndReservationFilterDto>
}
