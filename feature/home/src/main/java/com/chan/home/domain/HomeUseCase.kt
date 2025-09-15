package com.chan.home.domain

import com.chan.home.domain.repository.HomeRepository
import javax.inject.Inject

data class HomeUseCases(
    val homeBanner: HomeBannerUseCase,
    val popularProducts: HomePopularProductsUseCase,
    val saleProducts: HomeSaleProductsUseCase,
    val categoryTabs: HomeCategoryTabsUseCase,
    val categoryRankingProducts: HomeCategoryRankingProductsUseCase
)

class HomeBannerUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.getBanners()
}

class HomePopularProductsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(limit: Int) = homeRepository.getPopularProducts(limit)
}

class HomeSaleProductsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(limit: Int) = homeRepository.getSaleProducts(limit)
}

class HomeCategoryTabsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.getCategoryTabs()
}

class HomeCategoryRankingProductsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(categoryId: String) =
        homeRepository.getRankingProductsByCategoryId(categoryId)
}