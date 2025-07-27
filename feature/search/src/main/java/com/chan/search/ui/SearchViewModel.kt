package com.chan.search.ui

import androidx.lifecycle.viewModelScope
import com.chan.android.BaseViewModel
import com.chan.android.LoadingState
import com.chan.search.domain.repository.SearchRepository
import com.chan.search.ui.mappers.toSearchModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    init {
        observeSearchQuery()
    }

    override fun setInitialState() = SearchContract.State()

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewState
            .map { it.search }
            .distinctUntilChanged()
            .debounce(300L)
            .onEach { query ->
                searchProducts(query)
            }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnSearchChanged -> {
                setState { copy(search = event.search) }
            }
            is SearchContract.Event.OnClickSearchResult -> {
                TODO("검색어에 맞는 리스트 가져오기 + 상세화면 바인딩 예정")
            }
            SearchContract.Event.OnClickClearSearch -> {
                setState { copy(search = "", searchResults = emptyList()) }
            }
            SearchContract.Event.OnClickSearch -> TODO()
        }
    }

    private fun searchProducts(query: String) {
        if (query.isBlank()) {
            setState { copy(searchResults = emptyList()) }
            return
        }

        handleRepositoryCall(
            call = { searchRepository.searchProductName(query).map { it.toSearchModel() } },
            onSuccess = { searchResults -> copy(searchResults = searchResults) }
        )
    }

    private fun <T> handleRepositoryCall(
        call: suspend () -> T,
        onSuccess: SearchContract.State.(T) -> SearchContract.State
    ) {
        viewModelScope.launch {
            setState { copy(loadingState = LoadingState.Loading) }
            try {
                val result = call()
                setState { onSuccess(result).copy(loadingState = LoadingState.Success) }
            } catch (e: Exception) {
                setState { copy(loadingState = LoadingState.Error) }
                setEffect { SearchContract.Effect.ShowError(e.message.toString()) }
            }
        }
    }
} 