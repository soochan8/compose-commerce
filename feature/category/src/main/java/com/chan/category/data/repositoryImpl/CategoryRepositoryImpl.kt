package com.chan.category.data.repositoryImpl

import com.chan.category.data.datasource.CategoryRemoteDataSource
import com.chan.category.data.mapper.toCategoryVO
import com.chan.category.domain.CategoryRepository
import com.chan.category.domain.vo.CategoryVO
import com.chan.database.dao.CategoryDao
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val remoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getAllCategories(): List<CategoryVO> {
        if (categoryDao.getAllCategories().isEmpty()) {
            categoryDao.insertAllCategories(remoteDataSource.loadCategoriesFromAsset())
        }
        return categoryDao.getAllCategories().map { it.toCategoryVO() }
    }
}