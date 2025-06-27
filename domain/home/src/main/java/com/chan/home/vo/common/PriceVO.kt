package com.chan.home.vo.common

data class PriceVO(
    val discountPercent: Int?,
    val discountedPrice: Int,
    val originalPrice: Int,
)