package com.chan.search.ui.composables.result

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chan.android.model.ProductModel
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.dividerColor
import com.chan.search.ui.composables.result.composables.FilterChipRow
import com.chan.search.ui.composables.result.composables.TabRow
import com.chan.search.ui.composables.result.composables.productGrid
import com.chan.search.ui.contract.SearchContract

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultScreenContent(
    state: SearchContract.State,
    onEvent: (SearchContract.Event) -> Unit,
    onProductClick: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.searchResultProducts) {
        if (state.searchResultProducts.isNotEmpty()) {
            lazyListState.scrollToItem(0)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        item {
            TabRow(
                tabs = state.resultTabRow.tabs,
                selectedTabIndex = state.resultTabRow.resultSelectedTabIndex,
                onTabSelected = { selectedTabIndex ->
                    onEvent(SearchContract.Event.TabRow.OnResultTabSelected(selectedTabIndex))
                }
            )
        }

        stickyHeader {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = White
            ) {
                Column {
                    FilterChipRow(
                        filters = state.filter.filterChips,
                        onToggleFilter = {
                            onEvent(
                                SearchContract.Event.Filter.OnFilterChipClicked(
                                    it
                                )
                            )
                        },
                        onFilterClick = { onEvent(SearchContract.Event.Filter.OnFilterClick) },
                    )
                    HorizontalDivider(color = dividerColor, thickness = 1.dp)
                }
            }
        }

        when (state.resultTabRow.resultSelectedTabIndex) {
            0 -> { // 상품
                productTabContent(
                    products = state.searchResultProducts,
                    onProductClick = onProductClick
                )
            }

            1 -> reviewTabContent()
            2 -> contentTabContent()
        }
    }
}

private fun LazyListScope.productTabContent(
    products: List<ProductModel>,
    onProductClick: (String) -> Unit
) {
    item {
        SearchResultListHeader(products)
    }
    productGrid(
        products = products,
        onProductClick = onProductClick
    )
}

private fun LazyListScope.reviewTabContent() {
    item { Text("후기 탭 내용") }
}

private fun LazyListScope.contentTabContent() {
    item { Text("콘텐츠 탭 내용") }
}

@Composable
private fun SearchResultListHeader(products: List<ProductModel>) {
    Text(text = "총 ${products.size}개", modifier = Modifier.padding(Spacing.spacing4))
}