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
        state = state,
        onEvent = onEvent,
        onProductClick = { productId ->
            onProductClick(productId)
        }
    )
}