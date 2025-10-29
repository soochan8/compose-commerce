package com.chan.category.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.model.ProductModel
import com.chan.android.model.ProductsModel
import com.chan.category.ui.model.detail.CategoryDetailTabsModel

class CategoryDetailContract {

    sealed class Event : ViewEvent {
        data class CategoryDetailLoad(val categoryId: String) : Event()
        data class CategoryTabSelected(val categoryId: String) : Event()
        data class OnProductClick(val productId: String) : Event()
        data class OnAddToCartClick(val productId: String) : Event()
    }

    data class State(
        val loadingState: LoadingState = LoadingState.Idle,
        val selectedCategoryTabId: String? = null,
        val selectedCategoryTabIndex: Int = 0,
        val categoryNames: List<CategoryDetailTabsModel> = emptyList(),
        val categoryDetailList: List<ProductModel> = emptyList(),
        val productListByCategory: List<ProductsModel> = emptyList()
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val errorMessage: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToProductDetail(val productId: String) : Navigation()
            data class ToCartPopupRoute(val productId: String) : Navigation()
        }
    }
}