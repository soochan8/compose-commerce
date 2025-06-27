package com.chan.home.data.repository

import com.chan.home.data.dao.HomeSaleProductDao
import com.chan.home.data.datasource.HomeSaleProductSource
import com.chan.home.data.entity.home.toDomain
import com.chan.home.domain.repository.HomeSaleProductRepository
import com.chan.home.domain.vo.HomeSaleProductVO
import javax.inject.Inject

class HomeSaleProductRepositoryImpl @Inject constructor(
    private val homeSaleProductDao: HomeSaleProductDao,
    private val dataSource: HomeSaleProductSource
) : HomeSaleProductRepository {

    override suspend fun getSaleProducts(): List<HomeSaleProductVO> {
        homeSaleProductDao.deleteAll()
        if (homeSaleProductDao.getSaleProductAll().isEmpty()) {
            val dummySaleProducts = dataSource.getHomeSaleProducts()
            homeSaleProductDao.insertAll(dummySaleProducts)
        }
        return homeSaleProductDao.getSaleProductAll().map { it.toDomain() }
    }
}