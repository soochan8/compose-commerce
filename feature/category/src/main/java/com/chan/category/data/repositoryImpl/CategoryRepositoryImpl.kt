package com.chan.category.data.repositoryImpl

import com.chan.category.data.datasource.LocalDataSource
import com.chan.category.data.datasource.RemoteDataSource
import com.chan.category.data.mapper.toCategoriesDto
import com.chan.category.data.mapper.toDomain
import com.chan.category.domain.CategoryRepository
import com.chan.category.domain.dto.CategoriesDto
import com.chan.category.domain.vo.CategoryVO
import com.chan.database.dao.ProductDao
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : CategoryRepository {
    override suspend fun getCategories(): List<CategoriesDto> {
        return productDao.getAll().map { it.toCategoriesDto() }
    }
}