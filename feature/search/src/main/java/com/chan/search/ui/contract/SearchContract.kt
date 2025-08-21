package com.chan.search.ui.contract

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.android.model.ProductModel
import com.chan.search.ui.model.SearchHistoryModel
import com.chan.search.ui.model.SearchResultFilterChipModel
import com.chan.search.ui.model.SearchResultModel
import com.chan.search.ui.model.TrendingSearchModel
import com.chan.search.ui.model.filter.DeliveryOption
import com.chan.search.ui.model.filter.FilterCategoriesModel
import com.chan.search.ui.model.result.SearchResultTab

class SearchContract {

    sealed class Event : ViewEvent {
        data class OnSearchChanged(val search: String) : Event()
        object OnClickClearSearch : Event()
        object OnSearchTextFocus : Event()
        data class OnClickSearchProduct(val clickedProductName: String) : Event()

        data class OnAddSearchKeyword(val search: String) : Event()
        data class OnRemoveSearchKeyword(val search: String) : Event()
        object OnClearAllRecentSearches : Event()

        sealed class Filter : Event() {
            data class OnDeliveryOptionChanged(val option: DeliveryOption) : Filter()
            data class OnCategoryHeaderClick(val categoryName: String) : Filter()
            data class OnSubCategoryClick(val subCategoryName: String) : Filter()
            data class OnFilterChipClicked(val chip: SearchResultFilterChipModel) : Filter()
            object OnCategoryClick : Filter()
            object OnFilterClick : Filter()
            object OnClear : Filter()
        }

        sealed class TabRow: Event() {
            data class OnResultTabSelected(val index: Int): Event()
        }
    }

    data class State(
        val search: String = "",
        val searchResults: List<SearchResultModel> = emptyList(),
        val loadingState: LoadingState = LoadingState.Idle,

        val recentSearches: List<SearchHistoryModel> = emptyList(),

        val recommendedKeywords: List<String> = emptyList(),
        val trendingSearches: List<TrendingSearchModel> = emptyList(),

        val searchResultProducts: List<ProductModel> = emptyList(),
        val currentTime: String = "",
        val showSearchResult: Boolean = false,

        val filter: FilterState = FilterState(),
        val resultTabRow: TabRow = TabRow()


    ) : ViewState

    data class TabRow(
        val resultSelectedTabIndex : Int = 0,
        val tabs: List<SearchResultTab> = SearchResultTab.allTabs
    )

    data class FilterState (
        val showFilter: Boolean = false,
        val selectedDeliveryOption: DeliveryOption? = null,
        val filterChips: List<SearchResultFilterChipModel> = emptyList(),
        val categoryFilters: List<FilterCategoriesModel> = emptyList(),
        val expandedCategoryName: String? = null,
        val selectedSubCategories: Set<String> = emptySet(),
        val isCategorySectionExpanded: Boolean = false,
        val filteredProductCount: Int = 0
    )

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
    }
} 