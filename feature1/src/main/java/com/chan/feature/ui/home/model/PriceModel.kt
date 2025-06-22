package com.chan.feature.ui.home.model

data class PriceModel(
    val discountPercent: Int?,
    val discountedPrice: Int,
    val originalPrice: Int,
)