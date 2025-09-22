package com.chan.search.ui.actions

import androidx.compose.runtime.Stable
import com.chan.search.ui.contract.SearchContract

@Stable
class SearchActions(private val onEvent: (SearchContract.Event) -> Unit) {
    val onBackClick: () -> Unit = { onEvent(SearchContract.Event.OnBackStackClick) }
    val onCartClick: () -> Unit = { onEvent(SearchContract.Event.OnCartClick) }
    val onClearSearch: () -> Unit = { onEvent(SearchContract.Event.OnClickClearSearch) }
    val onSearchTextFocus: () -> Unit = { onEvent(SearchContract.Event.OnSearchTextFocus) }
    val onClearAllRecentSearches: () -> Unit =
        { onEvent(SearchContract.Event.OnClearAllRecentSearches) }

    val filter: FilterActions = FilterActions(onEvent)

    fun onSearchChanged(search: String) = onEvent(SearchContract.Event.OnSearchChanged(search))
    fun onSearchClick(search: String) = onEvent(SearchContract.Event.OnAddSearchKeyword(search))
    fun onProductClick(productId: String) = onEvent(SearchContract.Event.OnProductClick(productId))
    fun onSearchResultItemClick(productName: String) =
        onEvent(SearchContract.Event.OnClickSearchProduct(productName))

    fun onRemoveSearchKeyword(keyword: String) =
        onEvent(SearchContract.Event.OnProductClick(keyword))

    fun onAddSearchKeyword(keyword: String) =
        onEvent(SearchContract.Event.OnAddSearchKeyword(keyword))
}