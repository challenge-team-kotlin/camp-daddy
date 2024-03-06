package com.challengeteamkotlin.campdaddy.presentation.product

import com.challengeteamkotlin.campdaddy.application.product.ProductService
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.CreateProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.CreateProductResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping()
    fun createProduct(
        @RequestBody createProductRequest: CreateProductRequest
    ):ResponseEntity<CreateProductResponse> {

    }

    @PutMapping()
    fun editProduct(

    ):ResponseEntity<CreateProductResponse> {

    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable productId: Long,
    ) {

    }

    @GetMapping()
    fun getProductList(

    ):ResponseEntity<List<CreateProductResponse>> {

    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable productId: Long
    ):ResponseEntity<CreateProductResponse> {

    }

    @GetMapping("available-date")
    fun getAvailableDate() {

    }

    @GetMapping("members/{MemberId}")
    fun getMembersProductList(
        @PathVariable memberId: Long
    ) :ResponseEntity<List<CreateProductResponse>>{

    }

    @GetMapping("/categories")
    fun getCategoryList():List<String>{

    }

}