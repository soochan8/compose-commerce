package com.chan.domain

data class CartProductVO(
    val product: ProductsVO,   // 공용 상품
    val quantity: Int,         // 유저별 수량
    val isSelected: Boolean    // 유저별 선택 여부
)
