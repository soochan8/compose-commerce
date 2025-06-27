package com.chan.home.data.di

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
    abstract fun bindHomeBannerRepository(
        bannerRepositoryImpl: com.chan.home.data.repository.HomeBannerRepositoryImpl
    ): com.chan.home.domain.repository.HomeBannerRepository

    @Binds
    @Singleton
    abstract fun bindHomePopularItemRepository(
        bannerPopularItemRepositoryImpl: com.chan.home.data.repository.HomePopularItemRepositoryImpl
    ): com.chan.home.domain.repository.HomePopularItemRepository

    @Binds
    @Singleton
    abstract fun bindRankingCategoryRepository(
        rankingCategoryRepositoryImpl:
        com.chan.home.data.repository.RankingCategoryRepositoryImpl
    ): com.chan.home.domain.repository.RankingCategoryRepository

    @Binds
    @Singleton
    abstract fun bindHomeSaleProductRepository(
        homeSaleProductRepositoryImpl: com.chan.home.data.repository.HomeSaleProductRepositoryImpl
    ): com.chan.home.domain.repository.HomeSaleProductRepository
}

