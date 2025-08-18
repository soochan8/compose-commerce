package com.chan.search.ui.composables.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.android.model.ProductModel
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.viewmodel.SearchViewModel

@Composable
fun SearchResultScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    products: List<ProductModel>,
    onNavigateToFilter: () -> Unit,
    onProductClick: (String) -> Unit
) {
    val state by viewModel.viewState.collectAsState()


    SearchResultScreenContent(
        products = products,
        filters = state.filterChips,
        onToggleFilter = {
            viewModel.setEvent(SearchContract.Event.OnFilterChipClicked(it))
        },
        onNavigateToFilter = {
            onNavigateToFilter()
        },
        onProductClick = { productId ->
            onProductClick(productId)
        }
    )
}