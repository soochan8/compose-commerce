package com.chan.search.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.search.ui.viewmodel.SearchViewModel
import com.chan.search.ui.contract.SearchContract

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    SearchScreenContent(
        search = state.search,
        recentSearches = state.recentSearches,
        recommendedKeywords = state.recommendedKeywords,
        trendingSearches = state.trendingSearches,
        searchResults = state.searchResults,
        currentTime = state.currentTime,
        showSearchResult = state.showSearchResult,
        searchResultProducts = state.searchResultProducts,
        onSearchChanged = { viewModel.setEvent(SearchContract.Event.OnSearchChanged(it)) },
        onClearSearch = { viewModel.setEvent(SearchContract.Event.OnClickClearSearch) },
        onSearchClick = { viewModel.setEvent(SearchContract.Event.OnAddSearchKeyword(state.search)) },
        onRemoveSearchKeyword = { viewModel.setEvent(SearchContract.Event.OnRemoveSearchKeyword(it)) },
        onClearAllRecentSearches = { viewModel.setEvent(SearchContract.Event.OnClearAllRecentSearches) },
        onSearchResultItemClick = { viewModel.setEvent(SearchContract.Event.OnClickSearchResult(it)) },
        onClickBack = {
            navController.popBackStack()
        },
        onClickCart = {
            // TODO : Cart 로 이동
        }
    )
}