package com.chan.home.home

import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.home.model.HomeBannerModel
import com.chan.home.model.HomePopularItemModel
import com.chan.home.model.HomeSaleProductModel
import com.chan.home.model.RankingCategoryModel

class HomeContract {
    sealed class Event : ViewEvent {
        object BannerLoad : Event()
        object PopularItemLoad : Event()
        object RankingCategoriesLoad : Event()
        object SaleProducts : Event()

        object Retry : Event()
    }

    data class State(
        val bannerList: List<HomeBannerModel> = emptyList(),
        val popularItemList: List<HomePopularItemModel> = emptyList(),
        val rankingCategories: List<RankingCategoryModel> = emptyList(),
        val saleProductList: List<HomeSaleProductModel> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect()
    }
}