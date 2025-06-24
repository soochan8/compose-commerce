package com.chan.home.model

import androidx.compose.runtime.Immutable

@Immutable
data class HomeSaleProductModel(
    val id : Int,
    val imageUrl: String,
    val productName: String,
    val price: PriceModel,
    val tags: List<String>?
)