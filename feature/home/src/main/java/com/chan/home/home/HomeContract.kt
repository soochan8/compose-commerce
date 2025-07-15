package com.chan.home.home

import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.home.model.HomeBannerModel
import com.chan.home.model.HomePopularItemModel
import com.chan.home.model.HomeRankingCategoryProductModel
import com.chan.home.model.HomeRankingCategoryTabModel
import com.chan.home.model.HomeSaleProductModel

class HomeContract {
    sealed class Event : ViewEvent {
        object BannerLoad : Event()
        object PopularItemLoad : Event()
        object RankingCategoryTabsLoad : Event()
        object SaleProducts : Event()
        object Retry : Event()

        data class RankingTabSelected(val categoryId: String) : Event()
    }

    data class State(
        val bannerList: List<HomeBannerModel> = emptyList(),
        val popularItemList: List<HomePopularItemModel> = emptyList(),
        val rankingCategoryTabs: List<HomeRankingCategoryTabModel> = emptyList(),
        val rankingCategories: List<HomeRankingCategoryProductModel> = emptyList(),
        val saleProductList: List<HomeSaleProductModel> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
    }
}