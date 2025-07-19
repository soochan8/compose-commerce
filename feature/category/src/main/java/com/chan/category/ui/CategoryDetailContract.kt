package com.chan.category.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.model.ProductModel
import com.chan.category.ui.model.detail.CategoryDetailTabsModel

class CategoryDetailContract {

    sealed interface Event : ViewEvent {
        data class CategoryDetailLoad(val categoryId: String) : Event
    }

    data class State(
        val loadingState: LoadingState = LoadingState.Idle,
        val categoryNames: List<CategoryDetailTabsModel> = emptyList(),
        val categoryDetailList: List<ProductModel> = emptyList()
    ) : ViewState

    sealed interface Effect : ViewEffect {
        data class ShowError(val errorMessage: String) : Effect
    }
}