package com.chan.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.chan.home.model.HomeBannerModel

@Composable
fun HomeBanner(
    bannerList: List<HomeBannerModel>,
    modifier: Modifier = Modifier
) {

    if (bannerList.isEmpty()) {
        return
    }

    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE }
    )


    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
        ) { page ->
            val index = (page - initialPage).mod(bannerList.size)
            val banner = bannerList[index]
            AsyncImage(
                model = banner.imageUrl,
                contentDescription = "Banner Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}