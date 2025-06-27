package com.chan.home.data.repository

import com.chan.database.dao.RankingCategoryDao
import com.chan.home.data.datasource.RankingCategoryDataSource
import com.chan.home.data.mapper.toDomain
import com.chan.home.domain.repository.RankingCategoryRepository
import com.chan.home.domain.vo.RankingCategoryVO
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