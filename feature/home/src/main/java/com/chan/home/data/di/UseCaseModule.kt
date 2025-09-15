package com.chan.home.data.di

import com.chan.home.domain.HomeBannerUseCase
import com.chan.home.domain.HomeCategoryRankingProductsUseCase
import com.chan.home.domain.HomeCategoryTabsUseCase
import com.chan.home.domain.HomePopularProductsUseCase
import com.chan.home.domain.HomeSaleProductsUseCase
import com.chan.home.domain.HomeUseCases
import com.chan.home.domain.usecase.RankingProductsUseCase
import com.chan.home.domain.usecase.RankingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideHomeUseCases(
        homeBannerUseCase: HomeBannerUseCase,
        homePopularProductsUseCase: HomePopularProductsUseCase,
        homeSaleProductsUseCase: HomeSaleProductsUseCase,
        homeCategoryTabsUseCase: HomeCategoryTabsUseCase,
        homeCategoryRankingProductsUseCase: HomeCategoryRankingProductsUseCase
    ): HomeUseCases {
        return HomeUseCases(
            homeBanner = homeBannerUseCase,
            popularProducts = homePopularProductsUseCase,
            saleProducts = homeSaleProductsUseCase,
            categoryTabs = homeCategoryTabsUseCase,
            categoryRankingProducts = homeCategoryRankingProductsUseCase
        )
    }

    @Provides
    fun provideRankingUseCases(
        rankingProductsUseCase: RankingProductsUseCase
    ): RankingUseCase {
        return RankingUseCase(
            rankingProductsUseCase = rankingProductsUseCase
        )
    }
}
