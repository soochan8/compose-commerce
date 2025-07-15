package com.chan.home.domain.vo

data class ProductVO(
    val productId: String,
    val productName: String,
    val brandName: String,
    val imageUrl: String,
    val originalPrice: Int,
    val discountPercent: Int,
    val discountPrice: Int,
    val tags: List<String>,
    val reviewRating: Float,
    val reviewCount: Int
) 