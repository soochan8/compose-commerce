package com.chan.home.model

data class PriceModel(
    val discountPercent: Int?,
    val discountedPrice: Int,
    val originalPrice: Int,
)