package com.chan.database.entity

data class PriceEntity(
    val discountPercent: Int?,
    val discountedPrice: Int,
    val originalPrice: Int,
)