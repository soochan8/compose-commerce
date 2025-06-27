package com.chan.home.repository

import com.chan.home.vo.RankingCategoryVO

interface RankingCategoryRepository {
    suspend fun getRankingCategories(): List<RankingCategoryVO>
}