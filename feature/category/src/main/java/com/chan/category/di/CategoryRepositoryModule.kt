package com.chan.category.di

import com.chan.category.data.repositoryImpl.CategoryRepositoryImpl
import com.chan.category.domian.CategoryRepository
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
    abstract fun bindCategoryRepository (
        categoryRepositoryImpl: CategoryRepositoryImpl
    ) : CategoryRepository
}