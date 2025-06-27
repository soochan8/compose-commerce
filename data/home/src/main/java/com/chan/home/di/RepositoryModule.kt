package com.chan.home.di

import com.chan.home.repository.HomeBannerRepositoryImpl
import com.chan.home.repository.HomePopularItemRepositoryImpl
import com.chan.home.repository.HomeSaleProductRepositoryImpl
import com.chan.home.repository.RankingCategoryRepositoryImpl
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
        bannerRepositoryImpl: HomeBannerRepositoryImpl
    ): com.chan.home.domain.repository.HomeBannerRepository

    @Binds
    @Singleton
    abstract fun bindHomePopularItemRepository(
        bannerPopularItemRepositoryImpl: HomePopularItemRepositoryImpl
    ): com.chan.home.domain.repository.HomePopularItemRepository

    @Binds
    @Singleton
    abstract fun bindRankingCategoryRepository(
        rankingCategoryRepositoryImpl:
        RankingCategoryRepositoryImpl
    ): com.chan.home.domain.repository.RankingCategoryRepository

    @Binds
    @Singleton
    abstract fun bindHomeSaleProductRepository(
        homeSaleProductRepositoryImpl: HomeSaleProductRepositoryImpl
    ): com.chan.home.domain.repository.HomeSaleProductRepository
}

