package com.chan.category.data.repositoryImpl

import com.chan.category.data.datasource.LocalDataSource
import com.chan.category.data.datasource.RemoteDataSource
import com.chan.category.data.mapper.toDomain
import com.chan.category.domian.CategoryRepository
import com.chan.category.domian.vo.CategoryVO
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : CategoryRepository {
    override suspend fun getCategories(): List<CategoryVO> {
        if (localDataSource.getCategoryAll().isEmpty()) {
            val categories = remoteDataSource.getRankingCategories()
            localDataSource.insertAll(categories)
        }
        return localDataSource.getCategoryAll().map { it.toDomain() }
    }
}