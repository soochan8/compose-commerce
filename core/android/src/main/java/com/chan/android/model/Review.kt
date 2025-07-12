package com.chan.android.model

data class Review(
    val rating: Float,
    val reviewCount: Int
) {
    val reviewLabel = String.format("%.1f(%d)", rating, reviewCount)
}