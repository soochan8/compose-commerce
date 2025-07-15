package com.chan.category.di

import com.chan.category.data.repositoryImpl.CategoryDetailRepositoryImpl
import com.chan.category.data.repositoryImpl.CategoryRepositoryImpl
import com.chan.category.domain.CategoryDetailRepository
import com.chan.category.domain.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindCategoryDetailRepository(
        categoryDetailRepositoryImpl: CategoryDetailRepositoryImpl
    ): CategoryDetailRepository
}