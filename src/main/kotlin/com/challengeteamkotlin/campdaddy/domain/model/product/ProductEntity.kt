package com.challengeteamkotlin.campdaddy.domain.model.product

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE members SET is_deleted = true WHERE id = ?")
class ProductEntity(

//    @Column(name = "member_id", nullable = false)
//    val memberId :Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    val memberEntity: MemberEntity,

    @Column(name = "price_per_day", nullable = false)
    val pricePerDay:Long,

    @Column(name = "image_url", nullable =  true)
    var imageUrl: String?,

    @Column(name = "title", nullable = false)
    var title : String,

    @Column(name = "content", nullable = false)
    var content : String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    val category: Category

):BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id :Long? =null

    @OneToMany(mappedBy = "productEntity")
    val reviewEntity:List<ReviewEntity> = mutableListOf()

}
