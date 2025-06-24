package com.chan.home.repository

import com.chan.home.dao.HomeSaleProductDao
import com.chan.home.datasource.HomeSaleProductSource
import com.chan.home.entity.home.toDomain
import com.chan.home.vo.HomeSaleProductVO
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