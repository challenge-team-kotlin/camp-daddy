package com.challengeteamkotlin.campdaddy.domain.model.product

import jakarta.persistence.*

@Entity
@Table(name = "products_image")
class ProductImageEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val productEntity: ProductEntity,

        @Column(name = "image", nullable = false)
        val image: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    val id: Long? = null;
}