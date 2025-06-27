package com.chan.home.repository

import com.chan.home.dao.RankingCategoryDao
import com.chan.home.datasource.RankingCategoryDataSource
import com.chan.home.entity.ranking.toDomain
import com.chan.home.vo.RankingCategoryVO
import javax.inject.Inject
import kotlin.collections.map

class RankingCategoryRepositoryImpl @Inject constructor(
    private val rankingCategoryDao: RankingCategoryDao,
    private val rankingCategoryDataSource: RankingCategoryDataSource
) : RankingCategoryRepository {
    override suspend fun getRankingCategories(): List<RankingCategoryVO> {
        rankingCategoryDao.deleteAll()
        if (rankingCategoryDao.getRankingCategoryAll().isEmpty()) {
            val dummy = rankingCategoryDataSource.getRankingCategories()
            rankingCategoryDao.insertAll(dummy)
        }
        return rankingCategoryDao.getRankingCategoryAll().map { it.toDomain() }
    }
}