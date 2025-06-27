package com.chan.home.domain.repository

import com.chan.home.domain.vo.RankingCategoryVO

interface RankingCategoryRepository {
    suspend fun getRankingCategories(): List<RankingCategoryVO>
}