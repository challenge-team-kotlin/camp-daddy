package com.challengeteamkotlin.campdaddy.domain.model.product

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Embeddable
@Table(name = "products_image")
@SQLDelete(sql = "UPDATE products_image SET is_deleted = true WHERE product_image_id = ?")
class ProductImageEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val product: ProductEntity,

        @Column(name = "image_url", nullable = false)
        val imageUrl: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    val id: Long? = null;
}