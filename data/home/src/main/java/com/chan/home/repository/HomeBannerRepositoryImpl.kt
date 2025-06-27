package com.chan.home.repository

import com.chan.home.dao.HomeBannerDao
import com.chan.home.datasource.HomeBannerDataSource
import com.chan.home.entity.home.toDomain
import com.chan.home.vo.HomeBannerVO
import javax.inject.Inject
import kotlin.collections.map

class HomeBannerRepositoryImpl @Inject constructor(
    private val homeBannerDao: HomeBannerDao,
    private val homeBannerDataSource: HomeBannerDataSource
) : HomeBannerRepository {
    override suspend fun getBanners(): List<HomeBannerVO> {
        homeBannerDao.deleteAll()
        if (homeBannerDao.getBannerAll().isEmpty()) {
            val dummy = homeBannerDataSource.getDummyHomeBanners()
            homeBannerDao.insertAll(dummy)
        }
        return homeBannerDao.getBannerAll().map { it.toDomain() }
    }
}