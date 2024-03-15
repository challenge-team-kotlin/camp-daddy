package com.challengeteamkotlin.campdaddy.domain.model.reservation

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDate

@Entity
@Table(name = "reservations")
@SQLDelete(sql = "UPDATE reservations SET is_deleted = true WHERE reservation_id = ?")
@SQLRestriction("is_deleted = false")
class ReservationEntity(
    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate,

    @Column(name = "total_price", nullable = false)
    val totalPrice: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: ProductEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: MemberEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var reservationStatus: ReservationStatus

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    var id: Long? = null
}