package com.chan.home.data.repository

import com.chan.home.data.dao.HomePopularItemDao
import com.chan.home.data.datasource.HomePopularItemDataSource
import com.chan.home.data.entity.home.toDomain
import com.chan.home.domain.repository.HomePopularItemRepository
import com.chan.home.domain.vo.HomePopularItemVO
import javax.inject.Inject
import kotlin.collections.map

class HomePopularItemRepositoryImpl @Inject constructor(
    private val homePopularItemDao: HomePopularItemDao,
    private val dataSource: HomePopularItemDataSource
) : HomePopularItemRepository {
    override suspend fun getPopularItemAll(): List<HomePopularItemVO> {
        homePopularItemDao.deleteAll()
        if (homePopularItemDao.getPopularItemAll().isEmpty()) {
            val dummy = dataSource.getDummyPopularItems()
            homePopularItemDao.insertPopularItem(dummy)
        }
        return homePopularItemDao.getPopularItemAll().map { it.toDomain() }
    }
}