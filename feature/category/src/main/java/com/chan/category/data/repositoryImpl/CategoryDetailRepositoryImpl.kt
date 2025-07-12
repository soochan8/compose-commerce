package com.chan.category.data.repositoryImpl

import com.chan.category.data.datasource.CategoryDetailRemoteSource
import com.chan.category.data.mapper.toDomain
import com.chan.category.domain.CategoryDetailRepository
import com.chan.category.domain.vo.detail.CategoryDetailNamesVO
import com.chan.category.domain.vo.detail.CategoryDetailVO
import javax.inject.Inject

class CategoryDetailRepositoryImpl @Inject constructor(
    val remoteDataSource: CategoryDetailRemoteSource
) : CategoryDetailRepository {
    override suspend fun getCategoryNames(): List<CategoryDetailNamesVO> {
        val categoryNames = remoteDataSource.getCategoryNames()
        return categoryNames.map { it.toDomain() }
    }

    override suspend fun getCategoryDetails(): List<CategoryDetailVO> {
        val categoryDetails = remoteDataSource.getCategoryDetails()
        return categoryDetails.map { it.toDomain() }
    }
}