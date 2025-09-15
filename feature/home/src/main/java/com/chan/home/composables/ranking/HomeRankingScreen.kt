package com.chan.home.composables.ranking

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chan.android.model.ProductsModel
import com.chan.android.ui.CommonProductsCard
import com.chan.android.ui.theme.Spacing
import com.chan.home.home.HomeContract

@Composable
fun HomeRankingScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit
) {

    LaunchedEffect(Unit) {
        onEvent(HomeContract.Event.HomeRankingEvent.RankingProductsLoad)
    }

    val products = state.homeRankingState.rankingProducts?.collectAsLazyPagingItems()

    products?.let { lazyItems ->
        when (val refreshState = lazyItems.loadState.refresh) {
            is LoadState.Loading -> LoadingProducts()
            is LoadState.Error -> ErrorProducts(refreshState, lazyItems)
            is LoadState.NotLoading -> SuccessProducts(lazyItems, state, onEvent)
        }
    } ?: run {
        LoadingProducts()
    }
}

@Composable
private fun SuccessProducts(
    lazyItems: LazyPagingItems<ProductsModel>,
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(lazyItems.itemCount) { index ->
            lazyItems[index]?.let { product ->
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

@Composable
private fun ErrorProducts(refreshState: LoadState.Error, lazyItems: LazyPagingItems<ProductsModel>) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("데이터를 불러오지 못했습니다: ${refreshState.error.message}")
            Spacer(Modifier.height(Spacing.spacing2))
            Button(onClick = { lazyItems.retry() }) {
                Text("다시 시도")
            }
        }
    }
}

@Composable
private fun LoadingProducts() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}