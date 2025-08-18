package com.chan.search.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    SearchScreenContent(
        navController = navController,
        search = state.search,
        recentSearches = state.recentSearches,
        recommendedKeywords = state.recommendedKeywords,
        trendingSearches = state.trendingSearches,
        searchResults = state.searchResults,
        currentTime = state.currentTime,
        showSearchResult = state.showSearchResult,
        searchResultProducts = state.searchResultProducts,
        showFilter = state.showFilter,
        selectedDeliveryOption = state.selectedDeliveryOption,
        onSearchChanged = { viewModel.setEvent(SearchContract.Event.OnSearchChanged(it)) },
        onClearSearch = { viewModel.setEvent(SearchContract.Event.OnClickClearSearch) },
        onSearchClick = { viewModel.setEvent(SearchContract.Event.OnAddSearchKeyword(it)) },
        onSearchTextFocus = { viewModel.setEvent(SearchContract.Event.OnSearchTextFocus) },
        onRemoveSearchKeyword = { viewModel.setEvent(SearchContract.Event.OnRemoveSearchKeyword(it)) },
        onClearAllRecentSearches = { viewModel.setEvent(SearchContract.Event.OnClearAllRecentSearches) },
        onSearchResultItemClick = { viewModel.setEvent(SearchContract.Event.OnClickSearchProduct(it)) },
        onUpdateFilterClick = { viewModel.setEvent(SearchContract.Event.OnUpdateFilterClick) },
        onDeliveryOptionClick = { viewModel.setEvent(SearchContract.Event.OnDeliveryOptionChanged(it)) },
        onClickBack = {
            navController.popBackStack()
        },
        onClickCart = {
            // TODO : Cart 로 이동
        }
    )
}