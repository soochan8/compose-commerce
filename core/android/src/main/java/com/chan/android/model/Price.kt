package com.chan.android.model

import java.text.NumberFormat
import java.util.Locale

data class Price(
    val originPrice: Int,
    val discountedPrice: Int,
    val discountPercent: Int
) {
    val originPriceLabel = NumberFormat
        .getNumberInstance(Locale.getDefault())
        .format(originPrice)

    val discountedPriceLabel = NumberFormat
        .getNumberInstance(Locale.getDefault())
        .format(discountedPrice)
}