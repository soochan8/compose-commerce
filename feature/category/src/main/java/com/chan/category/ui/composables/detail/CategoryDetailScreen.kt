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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chan.android.NoRippleTheme
import com.chan.android.ProductCard
import com.chan.android.model.ProductModel
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.Gray
import com.chan.android.ui.theme.Spacing
import com.chan.category.ui.CategoryDetailContract
import com.chan.category.ui.model.detail.CategoryDetailTabsModel

@Composable
fun CategoryDetailScreen(
    state: CategoryDetailContract.State,
    onEvent: (CategoryDetailContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            if (state.categoryNames.isNotEmpty()) {
                CategoryDetailTopBar(
                    tabs = state.categoryNames,
                    selectedIndex = state.selectedCategoryTabIndex,
                    onTabSelected = { categoryId ->
                        onEvent(
                            CategoryDetailContract.Event.CategoryTabSelected(
                                categoryId
                            )
                        )
                    }
                )
            }
        }
    ) { padding ->
        CategoryDetailProductGrid(
            products = state.productListByCategory,
            modifier = Modifier.padding(top = padding.calculateTopPadding()),
            onEvent = onEvent
        )
    }
}

@Composable
fun CategoryDetailProductGrid(
    products: List<ProductModel>,
    modifier: Modifier = Modifier,
    onEvent: (CategoryDetailContract.Event) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(Spacing.spacing2)
    ) {
        items(
            items = products,
            key = { it.productId }
        ) { product ->
            ProductCard(
                product = product,
                onClick = { onEvent(CategoryDetailContract.Event.OnProductClick(it)) },
                onLikeClick = {},
                onCartClick = {}
            )
        }
    }
}

@Composable
fun CategoryDetailTopBar(
    tabs: List<CategoryDetailTabsModel>,
    selectedIndex: Int,
    onTabSelected: (String) -> Unit
) {
    ScrollableTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedIndex,
        edgePadding = Spacing.spacing4,
        indicator = { tabPositions ->
            Box(
                Modifier
                    .tabIndicatorOffset(tabPositions[selectedIndex])
                    .height(2.dp)
                    .background(Black)
            )
        },
    ) {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = { onTabSelected(title.categoryId) },
                    text = {
                        Text(
                            text = title.categoryName,
                            color = if (index == selectedIndex) Black else Gray
                        )
                    }
                )
            }
        }
    }
}
