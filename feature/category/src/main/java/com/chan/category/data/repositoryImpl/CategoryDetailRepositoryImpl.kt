package com.chan.category.data.repositoryImpl

import com.chan.category.data.mapper.toProductsVO
import com.chan.category.data.mapper.toTabsDomain
import com.chan.category.domain.CategoryDetailRepository
import com.chan.category.domain.vo.detail.CategoryDetailTabsVO
import com.chan.database.dao.CategoryDao
import com.chan.database.dao.ProductsDao
import com.chan.domain.ProductsVO
import javax.inject.Inject

class CategoryDetailRepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val categoryDao: CategoryDao
) : CategoryDetailRepository {

    override suspend fun getCategoryTabs(categoryId: String): List<CategoryDetailTabsVO> {
        return categoryDao.getParentCategory(categoryId).map { it.toTabsDomain() }
    }

    override suspend fun getProductsByCategory(categoryId: String): List<ProductsVO> {
        return productsDao.getProductsByCategoryId(categoryId).map { it.toProductsVO() }
    }

    override suspend fun getProductsAll(): List<ProductsVO> {
        return productsDao.getAllProducts().map { it.toProductsVO() }
    }
}