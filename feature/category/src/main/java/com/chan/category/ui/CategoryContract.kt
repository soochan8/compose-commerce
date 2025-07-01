package com.chan.category.ui

import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.category.ui.model.CategoryModel

class CategoryContract {
    sealed class Event : ViewEvent {
        object CategoriesLoad : Event()
        data class SelectCategory(val categoryId: Int): Event()
    }

    data class State(
        val categoryList: List<CategoryModel> = emptyList(),
        var selectedCategoryId: Int? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewEffect {
        //추후 네비게이션 추가 예정
    }
}