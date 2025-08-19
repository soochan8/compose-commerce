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
        filters = state.filterChips,
        onToggleFilter = {
            onEvent(SearchContract.Event.OnFilterChipClicked(it))
        },
        onNavigateToFilter = {
            onEvent(SearchContract.Event.OnUpdateFilterClick)
        },
        onProductClick = { productId ->
            onProductClick(productId)
        }
    )
}