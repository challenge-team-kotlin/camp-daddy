package com.challengeteamkotlin.campdaddy.presentation.product.context.repository

import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepositoryImpl
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.product.ProductJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductConfig {
    @Bean
    fun productRepository(productJpaRepository: ProductJpaRepository):ProductRepository{
        return ProductRepositoryImpl(productJpaRepository)
    }
}