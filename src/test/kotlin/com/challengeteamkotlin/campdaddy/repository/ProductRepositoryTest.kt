package com.challengeteamkotlin.campdaddy.repository

import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired

class ProductRepositoryTest(
    @Autowired
    var productRepository: ProductRepository
):BehaviorSpec(

    {
    Given("findAllByAvailableReservation 테스트"){
        When("정상적인 데이터"){


        }
    }
})