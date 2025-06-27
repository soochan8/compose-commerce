package com.chan.home.domain.vo.common

data class PriceVO(
    val discountPercent: Int?,
    val discountedPrice: Int,
    val originalPrice: Int,
)