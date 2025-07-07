package com.chan.category.ui.model.detail

import kotlin.text.format

data class ReviewModel(
    val rating: Float,
    val reviewCount: Int
) {
    val reviewLabel = String.format("%.1f(%d)", rating, reviewCount)
}