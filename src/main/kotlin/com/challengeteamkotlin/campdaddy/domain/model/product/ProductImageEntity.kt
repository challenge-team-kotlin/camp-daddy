package com.challengeteamkotlin.campdaddy.domain.model.product

import jakarta.persistence.*

@Entity
@Table(name = "products_image")
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
    var id: Long? = null;
}