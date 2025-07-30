package com.chan.search.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.search.ui.model.SearchHistoryModel
import com.chan.search.ui.model.SearchResultModel
import com.chan.search.ui.model.TrendingSearchModel

class SearchContract {

    sealed class Event : ViewEvent {
        data class OnSearchChanged(val search: String) : Event()
        object OnClickSearch : Event()
        object OnClickClearSearch : Event()
        data class OnClickSearchResult(val clickedProductName: String) : Event()

        data class OnAddSearchKeyword(val search: String) : Event()
        data class OnRemoveSearchKeyword(val search: String) : Event()
        object OnClearAllRecentSearches : Event()
    }

    data class State(
        val search: String = "",
        val searchResults: List<SearchResultModel> = emptyList(),
        val loadingState: LoadingState = LoadingState.Idle,

        val recentSearches: List<SearchHistoryModel> = emptyList(),

        val recommendedKeywords: List<String> = emptyList(),
        val trendingSearches: List<TrendingSearchModel> = emptyList(),

        val currentTime: String = ""
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
    }
} 