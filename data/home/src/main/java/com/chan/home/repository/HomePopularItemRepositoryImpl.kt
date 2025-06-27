package com.chan.home.repository

import com.chan.home.dao.HomePopularItemDao
import com.chan.home.datasource.HomePopularItemDataSource
import com.chan.home.entity.home.toDomain
import com.chan.home.vo.HomePopularItemVO
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