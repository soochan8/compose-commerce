package com.chan.home.model

data class RankingCardModel(
    val imageUrl: String,
    val productName: String,
    val price: PriceModel,
    val tags: List<String>,
    val isLiked: Boolean,
    val isInCart: Boolean
)