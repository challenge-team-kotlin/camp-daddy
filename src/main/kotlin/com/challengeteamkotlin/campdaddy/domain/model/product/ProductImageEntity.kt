package com.challengeteamkotlin.campdaddy.domain.model.product

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.DialectOverride.SQLSelect
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.Where

@Entity
@Embeddable
@Table(name = "products_image")
@SQLDelete(sql = "UPDATE products_image SET is_deleted = true WHERE product_image_id = ?")
@SQLRestriction("is_deleted = false")
class ProductImageEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val product: ProductEntity,

        @Column(name = "image_url", nullable = false)
        val imageUrl: String,
) : BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    var id: Long? = null;
}