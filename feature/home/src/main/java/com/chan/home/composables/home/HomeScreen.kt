package com.chan.home.composables.home

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.home.home.HomeContract
import com.chan.home.home.HomeViewModel
import com.chan.home.model.HomeTabItem
import com.chan.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state by homeViewModel.viewState.collectAsState()
    val tabList = remember { HomeTabItem.tabList() }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabList.size }
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.setEvent(HomeContract.Event.BannerLoad)
        homeViewModel.setEvent(HomeContract.Event.PopularItemLoad)
        homeViewModel.setEvent(HomeContract.Event.RankingCategoryTabsLoad)
        homeViewModel.setEvent(HomeContract.Event.SaleProducts)
    }

    HomeScreenContent(
        homeState = state,
        tabs = tabList,
        pagerState = pagerState,
        onTabClick = { index ->
            scope.launch {
                pagerState.scrollToPage(index)
            }
        },
        onRankingTabSelected = { categoryId ->
            homeViewModel.handleEvent(HomeContract.Event.RankingTabSelected(categoryId = categoryId))
        },
        onItemClick ={ productId ->
            navController.navigate(
                Routes.PRODUCT_DETAIL.productDetailRoute(productId)
            )
        }
    )
}