package com.chan.feature.ui.home.model

data class RankingCategoryModel(
    val rankingCategoryId: Int,
    val rankingCategoryName: String,
    val rankingCategoryItems : List<RankingCardModel>
)