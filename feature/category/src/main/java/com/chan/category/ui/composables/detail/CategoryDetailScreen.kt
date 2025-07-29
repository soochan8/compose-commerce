package com.chan.category.ui.composables.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.android.NoRippleTheme
import com.chan.android.ProductCard
import com.chan.category.ui.CategoryDetailContract
import com.chan.category.ui.CategoryDetailViewModel
import com.chan.category.ui.model.detail.CategoryDetailTabsModel

@Composable
fun CategoryDetailScreen(
    categoryId: String,
    viewModel: CategoryDetailViewModel = hiltViewModel(),
    onProductClick: (String) -> Unit
) {

    val state by viewModel.viewState.collectAsState()
    val selectedIndex =
        state.categoryNames.indexOfFirst { it.categoryId == state.selectedCategoryTabId }
            .coerceAtLeast(0)


    LaunchedEffect(categoryId) {
        viewModel.setEvent(CategoryDetailContract.Event.CategoryDetailLoad(categoryId))
    }

    Scaffold(
        topBar = {
            if (state.categoryNames.isNotEmpty()) {
                CategoryDetailTopBar(
                    tabs = state.categoryNames,
                    selectedIndex = selectedIndex,
                    onTabSelected = { index ->
                        val selectedCategoryIndex = state.categoryNames[index].categoryId
                        viewModel.setEvent(
                            CategoryDetailContract.Event.CategoryTabSelected(
                                selectedCategoryIndex
                            )
                        )
                    }
                )
            }
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                items = state.categoryDetailList,
                key = { it.productId }
            ) { product ->
                ProductCard(
                    product = product,
                    onClick = { onProductClick(it) },
                    onLikeClick = {},
                    onCartClick = {}
                )
            }
        }
    }
}

@Composable
fun CategoryDetailTopBar(
    tabs: List<CategoryDetailTabsModel>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedIndex,
        edgePadding = 16.dp,
        indicator = { tabPositions ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .height(2.dp)
                    .background(Color.Black)
            )
        },
    ) {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title.categoryName,
                            color = if (index == selectedIndex) Color.Black else Color.Gray
                        )
                    }
                )
            }
        }
    }
}
