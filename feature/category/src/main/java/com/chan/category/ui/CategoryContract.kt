package com.chan.category.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.category.ui.model.CategoryModel

class CategoryContract {
    sealed class Event : ViewEvent {
        object CategoriesLoad : Event()
        data class SelectCategory(val categoryId: String) : Event()
        data class CategoryScrolledIndex(val firstVisibleItemIndex: Int) : Event()
    }

    data class State(
        val categoryList: List<CategoryModel> = emptyList(),
        val selectedCategoryId: String? = null,
        val headerPositions: List<Pair<Int, String>> = emptyList(),
        val loadingState : LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val errorMessage: String) : Effect()
    }
}