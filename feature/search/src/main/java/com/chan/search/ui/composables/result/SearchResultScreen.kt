package com.chan.search.ui.composables.result

import androidx.compose.runtime.Composable
import com.chan.search.ui.contract.SearchContract

@Composable
fun SearchResultScreen(
    state: SearchContract.State,
    onEvent: (SearchContract.Event) -> Unit,
    onProductClick: (String) -> Unit
) {

    SearchResultScreenContent(
        products = state.searchResultProducts,
        filters = state.filter.filterChips,
        onToggleFilter = {
            onEvent(SearchContract.Event.Filter.OnFilterChipClicked(it))
        },
        onFilterClick = {
            onEvent(SearchContract.Event.Filter.OnFilterClick)
        },
        onProductClick = { productId ->
            onProductClick(productId)
        }
    )
}