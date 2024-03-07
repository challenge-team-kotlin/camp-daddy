package com.challengeteamkotlin.campdaddy.presentation.product.dto.request

/**
 * 이미지 정책.
 * 게시글 조회시에 프론트는 images를 presationtionList로 얕은복사한다.
 * 이후 게시글에 변경이 일어난다면,deleteList, addList를 두어 관리한다. (이미지 특성 상 수정은 삭제 or 생성뿐이다.)
  */
data class EditProductRequest(
        val productId:Long,
        val title:String,
        val content:String,
        val imageUrls : List<String>,
        val price : Int,
        val category: String
)
