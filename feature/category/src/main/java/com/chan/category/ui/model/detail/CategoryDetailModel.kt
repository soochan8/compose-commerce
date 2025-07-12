package com.chan.category.ui.model.detail

data class CategoryDetailModel(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: PriceModel,
    val review: ReviewModel?
)