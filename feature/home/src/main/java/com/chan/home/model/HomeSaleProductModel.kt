package com.chan.home.model

import androidx.compose.runtime.Immutable

@Immutable
data class HomeSaleProductModel(
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