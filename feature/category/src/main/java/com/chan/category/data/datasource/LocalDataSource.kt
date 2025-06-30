package com.chan.category.data.datasource

import com.chan.database.dao.category.CategoryDao
import com.chan.database.entity.category.CategoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao
) {
    suspend fun getCategoryAll(): List<CategoryEntity> = categoryDao.getCategoryAll()
    suspend fun insertAll(categories: List<CategoryEntity>) = categoryDao.insertAll(categories)
    suspend fun deleteAll() = categoryDao.deleteAll()
}