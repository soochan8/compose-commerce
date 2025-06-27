package com.chan.home.entity

data class PriceEntity(
    val discountPercent: Int?,
    val discountedPrice: Int,
    val originalPrice: Int,
)