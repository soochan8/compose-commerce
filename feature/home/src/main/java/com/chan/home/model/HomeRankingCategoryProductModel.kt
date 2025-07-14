package com.chan.home.model

data class HomeRankingCategoryProductModel(
    val productId: String,
    val productName: String,
    val brandName: String,
    val imageUrl: String,
    val originalPrice: String,
    val discountPercent: String,
    val discountPrice: String,
    val tags: List<String>,
    val reviewRating: String,
    val reviewCount: String
)