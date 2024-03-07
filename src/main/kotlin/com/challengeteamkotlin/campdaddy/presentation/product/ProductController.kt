package com.challengeteamkotlin.campdaddy.presentation.product

import com.challengeteamkotlin.campdaddy.application.product.ProductService
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.CreateProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.EditProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.CreateProductResponse
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.GetProductByMemberResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping()
    fun createProduct(
        @RequestBody createProductRequest: CreateProductRequest,
        @RequestParam memberId: Long
    ):ResponseEntity<CreateProductResponse> {
        ResponseEntity.status(HttpStatus.OK).body(productService.createProduct(createProductRequest, memberId))

    }

    @PutMapping()
    fun editProduct(
        @RequestBody editProductRequest: EditProductRequest,
        @RequestParam memberId: Long
    ):ResponseEntity<> =
        ResponseEntity.status(HttpStatus.OK).body(productService.editProduct(editProductRequest, memberId))


    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable productId: Long,
        @RequestParam memberId: Long
    ):ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId, memberId ))


    @GetMapping()
    fun getProductList(

    ):ResponseEntity<List<CreateProductResponse>> {

    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable productId: Long
    ):ResponseEntity<CreateProductResponse> {

    }

    @GetMapping("members/{memberId}")
    fun getMembersProductList(
        @PathVariable memberId: Long // JWT에서 받아올시에 삭제 예정.
    ) :ResponseEntity<List<GetProductByMemberResponse>> =
            ResponseEntity.status(HttpStatus.OK).body(productService.getMemberProductList(memberId))


    @GetMapping("/categories")
    fun getCategoryList():ResponseEntity<List<String>> =
        ResponseEntity.status(HttpStatus.OK).body(productService.getCategoryList())


}