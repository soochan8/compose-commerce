package com.chan.category.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.category.ui.model.CategoriesModel

class CategoryContract {
    sealed class Event : ViewEvent {
        data class OnCategorySidebarClick(val categoryId: String) : Event()
        data class OnCategoryClick(val categoryId: String) : Event()
        data class OnContentScroll(val firstVisibleIndex: Int) : Event()

        object OnSearchClick : Event()
    }

    data class State(
        val categories: List<CategoriesModel> = emptyList(),
        val selectedCategoryId: String? = null,
        val headerPositions: List<Pair<Int, String>> = emptyList(),
        val loadingState: LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val errorMessage: String) : Effect()
        data class ScrollToSidebar(val index: Int) : Effect()
        data class ScrollToContent(val index: Int) : Effect()
        sealed class Navigation : Effect() {
            data class ToCategoryDetail(val categoryId: String) : Navigation()
            object ToSearchRoute : Navigation()
        }
    }
}