package com.chan.home.mapper

import com.chan.home.model.HomeBannerModel
import com.chan.home.model.HomePopularItemModel
import com.chan.home.model.HomeSaleProductModel
import com.chan.home.model.PriceModel
import com.chan.home.vo.HomeBannerVO
import com.chan.home.vo.HomePopularItemVO
import com.chan.home.vo.HomeSaleProductVO
import com.chan.home.vo.common.PriceVO

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

fun HomeSaleProductVO.toPresentation(): HomeSaleProductModel {
    return HomeSaleProductModel(
        id = id,
        imageUrl = imageUrl,
        productName = productName,
        price = price.toPresentation(),
        tags = tags
    )
}

fun PriceVO.toPresentation(): PriceModel {
    return PriceModel(
        discountPercent = discountPercent,
        discountedPrice = discountedPrice,
        originalPrice = originalPrice
    )
}