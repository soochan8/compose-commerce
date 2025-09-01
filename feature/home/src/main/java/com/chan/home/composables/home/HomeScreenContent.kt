package com.chan.home.composables.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chan.home.composables.RecommendScreen
import com.chan.home.home.HomeContract
import com.chan.home.model.HomeTabItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeState: HomeContract.State,
    tabs: List<HomeTabItem>,
    pagerState: PagerState,
    onTabClick: (Int) -> Unit,
    onRankingTabSelected: (String) -> Unit,
    onItemClick: (productId: String) -> Unit,
    onCartClick: (productId: String) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopTab(
                tabs = tabs.map { stringResource(id = it.titleResId) },
                selectedTabIndex = pagerState.currentPage,
                onTabClick = onTabClick
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) { page ->
            when (tabs[page]) {
                HomeTabItem.Home -> {
                    HomePage(
                        homeState = homeState,
                        onRankingTabSelected = onRankingTabSelected,
                        onItemClick = onItemClick,
                        onCartClick = onCartClick
                    )
                }

                HomeTabItem.RecommendToday -> RecommendScreen()
                else -> {}
            }
        }
    }
}

@Composable
private fun HomePage(
    homeState: HomeContract.State,
    modifier: Modifier = Modifier,
    onRankingTabSelected: (String) -> Unit,
    onItemClick: (productId: String) -> Unit,
    onCartClick: (productId: String) -> Unit
) {
    val homeCategoryRankingPagerState = rememberPagerState { homeState.rankingCategoryTabs.size }

    LazyColumn(modifier = modifier) {
        item {
            if (homeState.bannerList.isNotEmpty()) {
                HomeBanner(bannerList = homeState.bannerList)
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (homeState.popularItemList.isNotEmpty()) {
                HomePopularItemList(
                    popularItem = homeState.popularItemList,
                    onItemClick = onItemClick
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (homeState.rankingCategories.isNotEmpty()) {
                HomeCategoryRanking(
                    categoryTabs = homeState.rankingCategoryTabs,
                    categories = homeState.rankingCategories,
                    pagerState = homeCategoryRankingPagerState,
                    onTabSelected = onRankingTabSelected,
                    onCartClick = onCartClick
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (homeState.saleProductList.isNotEmpty())
                HomeSaleProduct(saleProducts = homeState.saleProductList)
        }
    }
} 