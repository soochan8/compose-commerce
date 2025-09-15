package com.chan.home.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chan.home.composables.home.HomePage
import com.chan.home.composables.home.HomeTopTab
import com.chan.home.composables.ranking.HomeRankingScreen
import com.chan.home.home.HomeContract
import com.chan.home.model.HomeTabItem
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.topBars.size }
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            HomeTopTab(
                tabs = state.topBars.map { stringResource(id = it.titleResId) },
                selectedTabIndex = pagerState.currentPage,
                onTabClick = { index ->
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) { page ->
            when (state.topBars[page]) {
                HomeTabItem.Home -> {
                    HomePage(
                        state = state,
                        onEvent = onEvent
                    )
                }

                HomeTabItem.RecommendToday -> RecommendScreen()
                HomeTabItem.Ranking -> HomeRankingScreen(
                    state = state,
                    onEvent = onEvent
                )
                else -> {}
            }
        }
    }
}