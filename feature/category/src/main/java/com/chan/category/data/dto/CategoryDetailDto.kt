package com.chan.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDetailDto(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: PriceDto,
    val review: ReviewDto
) {
    data class PriceDto(
        val originPrice: Int,
        val discountedPrice: Int,
        val discountPercent: Int
    )

    data class ReviewDto(
        val rating: Float,
        val reviewCount: Int
    )
}