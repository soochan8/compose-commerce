package com.chan.home.data.entity

data class PriceEntity(
    val discountPercent: Int?,
    val discountedPrice: Int,
    val originalPrice: Int,
)