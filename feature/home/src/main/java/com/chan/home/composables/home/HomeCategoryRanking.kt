package com.chan.home.composables.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chan.android.ui.util.horizontalNestedScrollConnection
import com.chan.home.R
import com.chan.home.model.HomeRankingCategoryProductModel
import com.chan.home.model.HomeRankingCategoryTabModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeCategoryRanking(
    categoryTabs: List<HomeRankingCategoryTabModel>,
    categories: List<HomeRankingCategoryProductModel>,
    pagerState: PagerState,
    onTabSelected: (String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val nestedScrollConnection = horizontalNestedScrollConnection()

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress && categoryTabs.isNotEmpty()) {
            onTabSelected(categoryTabs[pagerState.currentPage].id)
        }
    }

    Text(
        text = stringResource(R.string.home_category_ranking),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 10.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
    )

    Column {
        // 카테고리 랭킹 탭
        CategoryTab(
            categoryTabs = categoryTabs,
            selectedCategoryTabIndex = pagerState.currentPage,
            onSelectedChanged = { idx ->
                scope.launch {
                    onTabSelected(categoryTabs[idx].id)
                    pagerState.scrollToPage(idx)
                }
            }
        )
        Spacer(Modifier.height(4.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .nestedScroll(nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                categories.forEach {
                    RankingCard(it)
                }
            }
        }
    }
}