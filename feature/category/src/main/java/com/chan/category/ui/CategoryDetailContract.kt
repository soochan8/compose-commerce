package com.chan.category.ui

import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.model.ProductModel
import com.chan.category.ui.model.detail.CategoryNamesModel

class CategoryDetailContract {

    sealed class Event : ViewEvent {
        object CategoryDetailNamesLoad : Event()
        object CategoryDetailListLoad : Event()
    }

    data class State(
        val categoryNames: List<CategoryNamesModel> = emptyList(),
        val categoryDetailList: List<ProductModel> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val errorMessage: String) : Effect()
    }
}