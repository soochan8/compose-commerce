package com.chan.search.ui

import com.chan.android.LoadingState
import com.chan.android.ViewEffect
import com.chan.android.ViewEvent
import com.chan.android.ViewState
import com.chan.search.ui.model.SearchResultModel

class SearchContract {

    sealed class Event : ViewEvent {
        data class OnSearchChanged(val search: String) : Event()
        object OnClickSearch : Event()
        object OnClickClearSearch : Event()
        data class OnClickSearchResult(val search: String) : Event()
    }

    data class State(
        val search: String = "",
        val searchResults: List<SearchResultModel> = emptyList(),
        val loadingState: LoadingState = LoadingState.Idle
    ) : ViewState

    sealed class Effect : ViewEffect {
        data class ShowError(val message: String) : Effect()
    }
} 