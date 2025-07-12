package com.chan.android.model

data class Price(
    val originPrice: Int,
    val discountedPrice: Int,
    val discountPercent: Int
)