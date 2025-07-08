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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.chan.home.R
import com.chan.home.model.RankingCategoryModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeCategoryRanking(
    categories: List<RankingCategoryModel>,
    pagerState: PagerState
) {

    val scope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return Offset(x = available.x, y = 0f)
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                return Velocity(x = available.x, y = 0f)
            }
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
            categories = categories,
            selectedCategoryTabIndex = pagerState.currentPage,
            onSelectedChanged = { idx ->
                scope.launch {
                    pagerState.animateScrollToPage(idx)
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
        ) { catPage ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                categories[catPage]
                    .rankingCategoryItems
                    .forEach { item ->
                        RankingCard(item)
                    }
            }
        }
    }
}