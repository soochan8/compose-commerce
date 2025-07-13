package com.chan.home.home

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.home.domain.repository.HomeBannerRepository
import com.chan.home.domain.repository.HomePopularItemRepository
import com.chan.home.domain.repository.HomeSaleProductRepository
import com.chan.home.domain.repository.RankingCategoryRepository
import com.chan.home.mapper.toPresentation
import com.chan.home.mapper.toPopularItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeBannerRepository: HomeBannerRepository,
    private val homePopularItemRepository: HomePopularItemRepository,
    private val rankingCategoryRepository: RankingCategoryRepository,
    private val saleProductRepository: HomeSaleProductRepository
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    override fun setInitialState() = HomeContract.State(

    )

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.BannerLoad -> getBanners()
            HomeContract.Event.Retry -> getBanners()
            HomeContract.Event.PopularItemLoad -> getPopularItems()
            HomeContract.Event.RankingCategoriesLoad -> getRankingCategories()
            HomeContract.Event.SaleProducts -> getSaleProducts()
        }
    }

    private fun getBanners() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val bannerList = homeBannerRepository.getBanners().map { it.toPresentation() }
            setState { copy(bannerList = bannerList, isLoading = false) }
            setEffect { HomeContract.Effect.ShowToast("배너 로딩") }
        }
    }

    private fun getPopularItems() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val popularItemList =
                homePopularItemRepository.getPopularProducts(20).map { it.toPopularItemModel() }
            setState { copy(popularItemList = popularItemList, isLoading = false) }
        }
    }

    private fun getRankingCategories() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val rankingCategoryList =
                rankingCategoryRepository.getRankingCategories().map { it.toPresentation() }
            setState { copy(rankingCategories = rankingCategoryList, isLoading = false) }
        }
    }

    private fun getSaleProducts() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            val saleProductList =
                saleProductRepository.getSaleProducts().map { it.toPresentation() }
            setState { copy(saleProductList = saleProductList, isLoading = false) }
        }
    }
}