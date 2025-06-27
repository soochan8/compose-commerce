package com.chan.feature.ui.home.composables

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.feature.ui.home.HomeContract
import com.chan.feature.ui.home.HomeViewModel
import com.chan.feature.ui.home.model.HomeTabItem

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state by homeViewModel.viewState
    val tabList = remember { HomeTabItem.tabList() }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabList.size }
    )
    var selectedCategoryTabIndex by remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit) {
        homeViewModel.setEvent(HomeContract.Event.BannerLoad)
        homeViewModel.setEvent(HomeContract.Event.PopularItemLoad)
        homeViewModel.setEvent(HomeContract.Event.RankingCategoriesLoad)
    }

    Scaffold(
        topBar = { HomeTopTab(tabList = tabList, pagerState = pagerState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                if (tabList[pagerState.currentPage] == HomeTabItem.Home) {
                    HomeBanner(bannerList = state.bannerList)
                    Spacer(modifier = Modifier.height(12.dp))
                    HomePopularItemList(popularItem = state.popularItemList)

                    CategoryTab(
                        categories = state.rankingCategories,
                        selectedCategoryTabIndex = selectedCategoryTabIndex,
                        onSelectedChanged = { selectedCategoryTabIndex = it },
                    )

                    val cards = state.rankingCategories
                        .getOrNull(selectedCategoryTabIndex)
                        ?.rankingCategoryItems
                        .orEmpty()

                    cards.forEach {
                        RankingCard(it)

                    }

                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (tabList[page]) {
                        HomeTabItem.RecommendToday -> RecommendScreen()
                        // 나머지 탭 추후 구현
                        else -> {}
                    }
                }
            }
        }
    }
}