package com.chan.domain

data class ProductsVO(
    val productId: String,
    val productName: String,
    val brandName: String,
    val imageUrl: String,
    val originalPrice: Int,
    val discountPercent: Int,
    val discountPrice: Int,
    val tags: List<String>,
    val reviewRating: Float,
    val reviewCount: Int,
    val categoryIds: List<String>
)