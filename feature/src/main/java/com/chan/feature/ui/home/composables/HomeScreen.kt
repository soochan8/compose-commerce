package com.chan.feature.ui.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.chan.feature.ui.home.model.HomeBannerModel

@Composable
fun HomeScreen() {

    val dummyBannerList = listOf(
        HomeBannerModel(1, "https://dummyimage.com/600x240/ff7f7f/ffffff&text=배너1"),
        HomeBannerModel(2, "https://dummyimage.com/600x240/ffbf7f/ffffff&text=배너2"),
        HomeBannerModel(3, "https://dummyimage.com/600x240/ffff7f/000000&text=배너3")
    )

    Column {
        HomeTopTab()
        HomeBanner(bannerList = dummyBannerList)
    }
}