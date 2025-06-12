package com.chan.feature.data.di

import com.chan.feature.data.datasource.TestDataSource
import com.chan.feature.data.repository.TestRepositoryImpl
import com.chan.feature.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    fun provideTestRepository(
        dataSource: TestDataSource
    ): TestRepository = TestRepositoryImpl(dataSource)

    @Provides
    fun provideTestDataSource(): TestDataSource {
        return TestDataSource()
    }

}