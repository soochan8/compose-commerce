package com.chan.home.composables.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chan.home.home.HomeContract

@Composable
fun HomePage(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit
) {
    val homeCategoryRankingPagerState = rememberPagerState { state.rankingCategoryTabs.size }

    LazyColumn {
        item {
            if (state.bannerList.isNotEmpty()) {
                HomeBanner(bannerList = state.bannerList)
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (state.popularProducts.isNotEmpty()) {
                HomePopularItemList(
                    product = state.popularProducts,
                    onProductClick = { productId ->
                        onEvent(HomeContract.Event.OnProductClicked(productId = productId))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (state.rankingCategories.isNotEmpty()) {
                HomeCategoryRanking(
                    categoryTabs = state.rankingCategoryTabs,
                    categories = state.rankingCategories,
                    pagerState = homeCategoryRankingPagerState,
                    onTabSelected = { categoryId ->
                        onEvent(HomeContract.Event.RankingTabSelected(categoryId = categoryId))
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (state.saleProductList.isNotEmpty())
                HomeSaleProduct(saleProducts = state.saleProductList)
        }
    }
}