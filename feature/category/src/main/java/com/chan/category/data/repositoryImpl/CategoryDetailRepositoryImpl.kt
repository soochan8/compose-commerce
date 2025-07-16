package com.chan.category.data.repositoryImpl

import com.chan.category.data.mapper.toDomain
import com.chan.category.data.mapper.toTabsDomain
import com.chan.category.domain.CategoryDetailRepository
import com.chan.category.domain.vo.ProductVO
import com.chan.category.domain.vo.detail.CategoryDetailTabsVO
import com.chan.database.dao.ProductDao
import javax.inject.Inject

class CategoryDetailRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : CategoryDetailRepository {
    override suspend fun getCategoryDetailTabs(subCategoryId: String): List<CategoryDetailTabsVO> {
        return productDao.getSiblingSubCategories(subCategoryId).map { it.toTabsDomain() }
    }

    override suspend fun getCategoryDetailProducts(subCategoryId: String): List<ProductVO> {
        return productDao.getProductsBySubCategoryId(subCategoryId).map { it.toDomain() }
    }

}