package com.chan.search.ui.model

import com.chan.search.domain.model.RankChange

data class TrendingSearchModel(
    val rank: Int,
    val keyword: String,
    val change: RankChange
)