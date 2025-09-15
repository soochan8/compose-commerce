package com.chan.home.data.di

import com.chan.home.data.repository.HomeRepositoryImpl
import com.chan.home.data.repository.RankingRepositoryImpl
import com.chan.home.domain.repository.HomeRepository
import com.chan.home.domain.repository.RankingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindRankingRepository(
        rankingRepositoryImpl: RankingRepositoryImpl
    ): RankingRepository
}

