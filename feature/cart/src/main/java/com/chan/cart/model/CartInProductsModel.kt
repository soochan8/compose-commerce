package com.chan.cart.model

data class CartInProductsModel(
    val productId: String,
    val productName: String,
    val brandName: String,
    val imageUrl: String,
    val originalPrice: String,
    val discountPercent: String,
    val discountPrice: Int,
    val discountPriceLabel: String,
    val tags: List<String>,
    val reviewRating: String,
    val reviewCount: String,
    val categoryIds: List<String>,
    val isSelected : Boolean,
    val quantity : Int
)