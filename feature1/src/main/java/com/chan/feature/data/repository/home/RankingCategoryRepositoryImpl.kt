package com.chan.feature.data.repository.home

import com.chan.feature.data.dao.RankingCategoryDao
import com.chan.feature.data.datasource.home.RankingCategoryDataSource
import com.chan.feature.data.entity.ranking.toDomain
import com.chan.feature.domain.repository.RankingCategoryRepository
import com.chan.feature.domain.vo.RankingCategoryVO
import javax.inject.Inject

class RankingCategoryRepositoryImpl @Inject constructor(
    private val rankingCategoryDao: RankingCategoryDao,
    private val rankingCategoryDataSource: RankingCategoryDataSource
) : RankingCategoryRepository {
    override suspend fun getRankingCategories(): List<RankingCategoryVO> {
        rankingCategoryDao.deleteAll()
        if(rankingCategoryDao.getRankingCategoryAll().isEmpty()) {
            val dummy = rankingCategoryDataSource.getRankingCategories()
            rankingCategoryDao.insertAll(dummy)
        }
        return rankingCategoryDao.getRankingCategoryAll().map { it.toDomain() }
    }
}