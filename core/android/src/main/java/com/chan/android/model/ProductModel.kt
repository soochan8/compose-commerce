package com.chan.android.model

data class ProductModel(
    val productId: String,
    val productName: String,
    val brandName: String,
    val imageUrl: String,
    val originalPrice: String,
    val discountPercent: String,
    val discountPrice: String,
    val tags: List<String>,
    val reviewRating: Float,
    val reviewCount: String
)