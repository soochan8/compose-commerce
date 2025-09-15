package com.chan.home.data.repository

import com.chan.database.dao.CategoryDao
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.ProductsDao
import com.chan.domain.ProductsVO
import com.chan.home.data.datasource.HomeBannerDataSource
import com.chan.home.data.mapper.toDomain
import com.chan.home.data.mapper.toProductsVO
import com.chan.home.data.mapper.toRankingCategoryTabVO
import com.chan.home.domain.repository.HomeRepository
import com.chan.home.domain.vo.HomeBannerVO
import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class HomeRepositoryImpl @Inject constructor(
    private val bannerDao: HomeBannerDao,
    private val homeBannerDataSource: HomeBannerDataSource,
    private val productsDao: ProductsDao,
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao
) : HomeRepository {

    override suspend fun getBanners(): List<HomeBannerVO> {
        bannerDao.deleteAll()
        if (bannerDao.getBannerAll().isEmpty()) {
            val dummy = homeBannerDataSource.getDummyHomeBanners()
            bannerDao.insertAll(dummy)
        }
        return bannerDao.getBannerAll().map { it.toDomain() }
    }

    override fun getPopularProducts(limit: Int): Flow<List<ProductsVO>> {
        return productsDao.getPopularProducts(limit).map { list -> list.map { it.toProductsVO() } }
    }

    override suspend fun getSaleProducts(limit: Int): List<ProductVO> {
        return productDao.getSaleProducts(limit).map { it.toDomain() }
    }

    override suspend fun getCategoryTabs(): List<RankingCategoryTabVO> {
        return categoryDao.getParentCategory().map { it.toRankingCategoryTabVO() }
    }

    override fun getRankingProductsByCategoryId(categoryId: String): Flow<List<ProductsVO>> {
        return productsDao.getProductsByCategoryIdLimit(categoryId)
            .map { list -> list.map { it.toProductsVO() } }
    }
}