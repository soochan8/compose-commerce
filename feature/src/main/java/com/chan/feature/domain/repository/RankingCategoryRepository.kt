package com.chan.feature.domain.repository

import com.chan.feature.domain.vo.RankingCategoryVO

interface RankingCategoryRepository {
    suspend fun getRankingCategories() : List<RankingCategoryVO>
}