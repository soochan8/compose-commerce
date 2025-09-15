package com.chan.home.composables.ranking

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.theme.Spacing
import com.chan.home.home.HomeContract

@Composable
fun HomeRankingScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit
) {

    LaunchedEffect(Unit) {
        Log.d("rankingInfo"," LaunchedEffect ")
        onEvent(HomeContract.Event.HomeRankingEvent.RankingProductsLoad)
    }

    //무한 스크롤
    state.homeRankingState.rankingProducts?.let { rankingProducts ->
        val products = rankingProducts.collectAsLazyPagingItems()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Spacing.spacing2)
        ) {
            items(products.itemCount) { index ->
                products[index]?.let { product ->
                    CommonProductsCard(
                        product = product,
                        onClick = { onEvent(HomeContract.Event.OnProductClicked(it)) },
                        onLikeClick = { onEvent(HomeContract.Event.OnLikedClick(it)) },
                        onCartClick = { onEvent(HomeContract.Event.OnCartClicked(it)) }
                    )
                }
            }
        }
    }
}