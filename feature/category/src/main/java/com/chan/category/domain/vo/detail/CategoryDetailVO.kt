package com.chan.category.domain.vo.detail


data class CategoryDetailVO(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val priceVO: PriceVO,
    val reviewVO: ReviewVO?
)