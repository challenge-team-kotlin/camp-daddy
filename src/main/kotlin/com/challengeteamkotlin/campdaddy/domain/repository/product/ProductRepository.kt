package com.challengeteamkotlin.campdaddy.domain.repository.product

import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product.ProductJpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindAllByAvailableReservationDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

interface ProductRepository : ProductJpaRepository {

    fun findByMemberId(memberId:Long):List<ProductEntity>

    @Query("""
        SELECT
            new com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindAllByAvailableReservationDto(
                p.pricePerDay,
                p.title,
                p.content,
                p_i.imageUrl,
                count (r.product.id)
            ) 
        FROM ProductEntity as p
            LEFT OUTER JOIN ReservationEntity as r
                ON p.id = r.product.id
            LEFT OUTER JOIN ProductImageEntity as p_i
                ON p.id = p_i.product.id
        WHERE
            p.id not in(
            select r.product.id
            from ReservationEntity as r
            where 
                r.startDate between :startDate and :endDate
                or r.endDate between :startDate and :endDate
                or (r.startDate <:startDate  and :endDate < r.endDate )
            )
        GROUP BY p.id
    """
    )
    fun findAllByAvailableReservation(startDate: LocalDate, endDate: LocalDate,pageable: Pageable):Page<List<FindAllByAvailableReservationDto>>
}
