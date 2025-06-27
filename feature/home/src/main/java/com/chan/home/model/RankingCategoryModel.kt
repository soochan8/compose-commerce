package com.chan.home.model

data class RankingCategoryModel(
    val rankingCategoryId: Int,
    val rankingCategoryName: String,
    val rankingCategoryItems : List<RankingCardModel>
)