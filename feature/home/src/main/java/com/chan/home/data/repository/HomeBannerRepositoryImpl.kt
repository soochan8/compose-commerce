package com.chan.home.data.repository

import com.chan.home.data.dao.HomeBannerDao
import com.chan.home.data.datasource.HomeBannerDataSource
import com.chan.home.data.entity.home.toDomain
import com.chan.home.domain.repository.HomeBannerRepository
import com.chan.home.domain.vo.HomeBannerVO
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