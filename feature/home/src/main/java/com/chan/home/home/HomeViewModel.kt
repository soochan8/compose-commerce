package com.chan.home.home

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.home.domain.repository.HomeBannerRepository
import com.chan.home.domain.repository.HomePopularItemRepository
import com.chan.home.domain.repository.HomeSaleProductRepository
import com.chan.home.domain.repository.RankingCategoryRepository
import com.chan.home.mapper.toPopularProductModel
import com.chan.home.mapper.toPresentation
import com.chan.home.mapper.toRankingCategoryProductModel
import com.chan.home.mapper.toRankingCategoryTabsModel
import com.chan.home.mapper.toSaleProductModel
import com.chan.home.model.HomeTabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeBannerRepository: HomeBannerRepository,
    private val homePopularItemRepository: HomePopularItemRepository,
    private val rankingCategoryRepository: RankingCategoryRepository,
    private val saleProductRepository: HomeSaleProductRepository
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    init {
        loadHomeTopBar()
    }

    override fun setInitialState() = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.BannerLoad -> getBanners()
            HomeContract.Event.Retry -> getBanners()
            HomeContract.Event.PopularItemLoad -> getPopularProducts()
            HomeContract.Event.RankingCategoryTabsLoad -> getRankingCategoryTabs()
            is HomeContract.Event.RankingTabSelected -> getRankingCategories(event.categoryId)
            HomeContract.Event.SaleProducts -> getSaleProducts()
            is HomeContract.Event.OnProductClicked -> productClicked(event.productId)
        }
    }

    private fun loadHomeTopBar() {
        setState { copy(topBars = HomeTabItem.tabList()) }
    }

    private fun productClicked(productId: String) {
        setEffect { HomeContract.Effect.Navigation.ToProductDetailRoute(productId) }
    }

    private fun getBanners() {
        handleRepositoryCall(
            call = {
                homeBannerRepository.getBanners().map { it.toPresentation() }
            },
            onSuccess = { bannerList -> copy(bannerList = bannerList) }
        )
    }

    private fun getPopularProducts() {
        viewModelScope.launch {
            homePopularItemRepository.getPopularProducts(20)
                .map { list -> list.map { it.toPopularProductModel() } }
                .collect { products ->
                    setState { copy(popularProducts = products) }
                }
        }
    }

    private fun getRankingCategoryTabs() {
        handleRepositoryCall(
            call = {
                rankingCategoryRepository.getCategoryTabs().map { it.toRankingCategoryTabsModel() }
            },
            onSuccess = { rankingCategoryTabs -> copy(rankingCategoryTabs = rankingCategoryTabs) },
            onFinally = { tabs ->
                if (tabs.isNotEmpty())
                    getRankingCategories(tabs.first().id)
            }
        )
    }

    private fun getRankingCategories(categoryId: String) {
        handleRepositoryCall(
            call = {
                rankingCategoryRepository.getRankingProductsByCategoryId(categoryId)
                    .map { it.toRankingCategoryProductModel() }
            },
            onSuccess = { rankingCategoryProducts -> copy(rankingCategories = rankingCategoryProducts) }
        )
    }

    private fun getSaleProducts() {
        handleRepositoryCall(
            call = { saleProductRepository.getSaleProducts(20).map { it.toSaleProductModel() } },
            onSuccess = { saleProducts -> copy(saleProductList = saleProducts) }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: HomeContract.State.(T) -> HomeContract.State,
        onFinally: suspend (T) -> Unit = {}
    ) {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            try {
                val result = call()
                setState { onSuccess(result) }
                onFinally(result)

            } catch (e: Exception) {
                setState { copy(isLoading = false, isError = true) }
                setEffect { HomeContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
}