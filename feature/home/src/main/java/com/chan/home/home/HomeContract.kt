package com.chan.home.home

import androidx.paging.PagingData
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.model.ProductsModel
import com.chan.home.model.HomeBannerModel
import com.chan.home.model.HomeRankingCategoryTabModel
import com.chan.home.model.HomeSaleProductModel
import com.chan.home.model.HomeTabItem
import kotlinx.coroutines.flow.Flow

class HomeContract {
    sealed class Event : ViewEvent {
        object BannerLoad : Event()
        object PopularItemLoad : Event()
        object RankingCategoryTabsLoad : Event()
        object SaleProducts : Event()
        object Retry : Event()

        data class RankingTabClicked(val index: Int) : Event()
        data class RankingTabSelected(val categoryId: String) : Event()
        data class OnProductClicked(val productId: String) : Event()
        data class OnLikedClick(val productId: String) : Event()
        data class OnCartClicked(val productId: String) : Event()

        sealed class HomeRankingEvent : ViewEvent {
            object RankingProductsLoad : Event()
        }
    }

    data class State(
        val topBars: List<HomeTabItem> = emptyList(),
        val bannerList: List<HomeBannerModel> = emptyList(),
        val popularProducts: List<ProductsModel> = emptyList(),
        val rankingCategoryTabs: List<HomeRankingCategoryTabModel> = emptyList(),
        val rankingCategories: List<ProductsModel> = emptyList(),
        val saleProductList: List<HomeSaleProductModel> = emptyList(),
        val selectedRankingTabIndex: Int = 0,
        val isLoading: Boolean = false,
        val isError: Boolean = false,

        val homeRankingState: HomeRankingState = HomeRankingState()
    ) : ViewState


    data class HomeRankingState(
        val rankingProducts: Flow<PagingData<ProductsModel>>? = null
    )

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
        sealed class Navigation : ViewEffect {
            data class ToProductDetailRoute(val productId: String) : Effect()
            data class ToCartPopupRoute(val productId: String) : Effect()
            data class ToCartRoute(val productId: String) : Effect()
        }
    }
}