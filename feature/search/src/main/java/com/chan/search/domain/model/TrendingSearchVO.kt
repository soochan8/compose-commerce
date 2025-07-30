package com.chan.search.domain.model
 
data class TrendingSearchVO(
    val rank: Int,
    val keyword: String,
    val change: RankChange
) 