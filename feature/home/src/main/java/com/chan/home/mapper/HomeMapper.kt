package com.chan.home.mapper

import com.chan.home.model.HomeBannerModel
import com.chan.home.model.HomePopularItemModel
import com.chan.home.vo.HomeBannerVO
import com.chan.home.vo.HomePopularItemVO

fun HomeBannerVO.toPresentation(): HomeBannerModel {
    return HomeBannerModel(
        id = id,
        imageUrl = imageUrl,
        title = title
    )
}

fun HomePopularItemVO.toPresentation(): HomePopularItemModel {
    return HomePopularItemModel(
        id = id,
        imageUrl = imageUrl,
        name = name,
        originPrice = originPrice,
        discountedPrice = discountedPrice,
        discountPercent = discountPercent,
        rating = rating
    )
}