package com.chan.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.chan.database.dao.ProductsDao
import com.chan.domain.ProductsVO
import com.chan.home.data.mapper.toProductsVO
import com.chan.home.domain.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao
) : RankingRepository {
    override fun getAllProducts(): Flow<PagingData<ProductsVO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { productsDao.getAllProductsPaging() }
        ).flow.map { pagingData ->
            pagingData.map { it.toProductsVO() }
        }
    }
}