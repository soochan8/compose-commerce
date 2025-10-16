package com.chan.home.composables.home.banner

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.chan.home.home.HomeContract
import com.chan.home.model.HomeBannerModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeBanner(
    state: HomeContract.BannerState,
    onEvent: (HomeContract.BannerEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state.banners.isEmpty()) {
        return
    }

    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE }
    )
    val bannerSize = remember(state.banners) { state.banners.size }

    fun currentBanner(page: Int): HomeBannerModel {
        val index = page.mod(bannerSize)
        return state.banners[index]
    }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
        ) { page ->
            val banner = remember(page) { currentBanner(page) }
            AsyncImage(
                model = remember(banner.imageUrl) { banner.imageUrl },
                contentDescription = "Banner Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onEvent(HomeContract.BannerEvent.SelectBanner(banner))
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}