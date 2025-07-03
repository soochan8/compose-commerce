package com.chan.category.ui

import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.category.ui.model.CategoryModel

class CategoryContract {
    sealed class Event : ViewEvent {
        object CategoriesLoad : Event()
        data class SelectCategory(val categoryId: Int): Event()
        data class CategoryScrolledIndex (val firstVisibleItemIndex: Int): Event()
    }

    data class State(
        val categoryList: List<CategoryModel> = emptyList(),
        val selectedCategoryId: Int? = null,
        val headerPositions : List<Pair<Int, Int>> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val errorMessage: String) : Effect()
    }
}