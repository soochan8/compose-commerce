package com.chan.android.model

data class ProductModel(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: Price,
    val review: Review?
)