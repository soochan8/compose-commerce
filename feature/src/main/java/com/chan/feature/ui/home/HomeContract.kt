package com.chan.feature.ui.home

import com.chan.core.base.ViewEffect
import com.chan.core.base.ViewEvent
import com.chan.core.base.ViewState
import com.chan.feature.ui.home.model.HomeBannerModel
import com.chan.feature.ui.home.model.HomePopularItemModel
import com.chan.feature.ui.home.model.RankingCategoryModel

class HomeContract {
    sealed class Event : ViewEvent {
        object BannerLoad : Event()
        object Retry : Event()

        object PopularItemLoad : Event()
        object RankingCategoriesLoad : Event()    // 추가

    }

    data class State(
        val bannerList: List<HomeBannerModel> = emptyList(),
        val popularItemList: List<HomePopularItemModel> = emptyList(),
        val rankingCategories: List<RankingCategoryModel> = emptyList(),  // 추가
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowToast(val message: String) : Effect()
    }
}