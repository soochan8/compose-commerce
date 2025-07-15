package com.chan.home.data.di

import com.chan.home.data.repository.HomeBannerRepositoryImpl
import com.chan.home.data.repository.HomePopularItemRepositoryImpl
import com.chan.home.data.repository.HomeSaleProductRepositoryImpl
import com.chan.home.data.repository.RankingCategoryRepositoryImpl
import com.chan.home.domain.repository.HomeBannerRepository
import com.chan.home.domain.repository.HomePopularItemRepository
import com.chan.home.domain.repository.HomeSaleProductRepository
import com.chan.home.domain.repository.RankingCategoryRepository
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
    ): HomeBannerRepository

    @Binds
    @Singleton
    abstract fun bindHomePopularItemRepository(
        bannerPopularItemRepositoryImpl: HomePopularItemRepositoryImpl
    ): HomePopularItemRepository

    @Binds
    @Singleton
    abstract fun bindRankingCategoryRepository(
        rankingCategoryRepositoryImpl: RankingCategoryRepositoryImpl
    ): RankingCategoryRepository

    @Binds
    @Singleton
    abstract fun bindHomeSaleProductRepository(
        homeSaleProductRepositoryImpl: HomeSaleProductRepositoryImpl
    ): HomeSaleProductRepository
}

