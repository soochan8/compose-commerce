package com.chan.home.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.home.home.HomeContract
import com.chan.home.home.HomeViewModel
import com.chan.home.model.HomeTabItem

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state by homeViewModel.viewState.collectAsState()
    val tabList = remember { HomeTabItem.tabList() }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabList.size }
    )

    val homeCategoryRankingPagerState = rememberPagerState { state.rankingCategories.size }

    LaunchedEffect(Unit) {
        homeViewModel.setEvent(HomeContract.Event.BannerLoad)
        homeViewModel.setEvent(HomeContract.Event.PopularItemLoad)
        homeViewModel.setEvent(HomeContract.Event.RankingCategoriesLoad)
        homeViewModel.setEvent(HomeContract.Event.SaleProducts)
    }

    Scaffold(
        topBar = { HomeTopTab(tabList = tabList, pagerState = pagerState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) { page ->
            when (tabList[page]) {
                HomeTabItem.Home -> {
                    LazyColumn {
                        item {
                            HomeBanner(bannerList = state.bannerList)
                            Spacer(modifier = Modifier.height(12.dp))

                            HomePopularItemList(popularItem = state.popularItemList)
                            Spacer(modifier = Modifier.height(12.dp))

                            HomeCategoryRanking(
                                categories = state.rankingCategories,
                                pagerState = homeCategoryRankingPagerState
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            HomeSaleProduct(saleProduct = state.saleProductList)
                        }
                    }
                }

                HomeTabItem.RecommendToday -> RecommendScreen()
                // 나머지 탭 추후 구현
                else -> {}
            }
        }
    }
}