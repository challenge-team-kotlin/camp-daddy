package com.challengeteamkotlin.campdaddy.presentation.product

import com.challengeteamkotlin.campdaddy.application.product.ProductService
import com.challengeteamkotlin.campdaddy.domain.model.product.Category
import com.challengeteamkotlin.campdaddy.domain.repository.product.dto.FindAllByAvailableReservationDto
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.CreateProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.EditProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.request.SearchProductRequest
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.GetProductByMemberResponse
import com.challengeteamkotlin.campdaddy.presentation.product.dto.response.ProductResponse
import kotlinx.coroutines.processNextEventInCurrentThread
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
import java.time.LocalDate

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping()
    fun createProduct(
        @RequestBody createProductRequest: CreateProductRequest,
        @RequestParam memberId: Long
    ):ResponseEntity<ProductResponse> =
        ResponseEntity.status(HttpStatus.OK).body(productService.createProduct(createProductRequest, memberId))

    @PutMapping()
    fun editProduct(
        @RequestBody editProductRequest: EditProductRequest,
        @RequestParam memberId: Long
    ):ResponseEntity<ProductResponse> =
        ResponseEntity.status(HttpStatus.OK).body(productService.editProduct(editProductRequest, memberId))


    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @PathVariable productId: Long,
        @RequestParam memberId: Long
    ):ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId, memberId ))


    @GetMapping()
    fun getProductList(
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate,
        @RequestParam preferRegion :String,
        @RequestParam category: Category,
        @RequestParam filterReservation: Boolean,
        @RequestParam lastProductId:Long,
        @RequestParam size :Int,
        @RequestParam page : Int,
    ):ResponseEntity<Page<List<FindAllByAvailableReservationDto>>> {

        val searchProductRequest = SearchProductRequest(
            startDate,endDate,preferRegion,category,filterReservation,lastProductId
        )
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductList(searchProductRequest,PageRequest.of(page,size)))
    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable productId: Long,
    ):ResponseEntity<ProductResponse> =
        ResponseEntity.status(HttpStatus.OK).body(productService.getProductDetail(productId))


    @GetMapping("members/{memberId}")
    fun getMembersProductList(
        @PathVariable memberId: Long
    ) :ResponseEntity<List<GetProductByMemberResponse>> =
            ResponseEntity.status(HttpStatus.OK).body(productService.getMemberProductList(memberId))


    @GetMapping("/categories")
    fun getCategoryList():ResponseEntity<List<String>> =
        ResponseEntity.status(HttpStatus.OK).body(productService.getCategoryList())


}