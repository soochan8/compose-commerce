package com.chan.feature.ui.mapper

import com.chan.feature.domain.vo.RankingCategoryVO
import com.chan.feature.ui.home.model.PriceModel
import com.chan.feature.ui.home.model.RankingCardModel
import com.chan.feature.ui.home.model.RankingCategoryModel

fun RankingCategoryVO.toPresentation(): RankingCategoryModel {
    return RankingCategoryModel(
        rankingCategoryId = rankingCategoryId,
        rankingCategoryName = rankingCategoryName,
        rankingCategoryItems = rankingCategoryItems.map { it.toPresentation() }
    )
}

fun RankingCategoryVO.RankingCategoryItemsVO.toPresentation(): RankingCardModel {
    return RankingCardModel(
        imageUrl = imageUrl,
        productName = productName,
        price = price.toPresentation(),
        tags = tags,
        isLiked = isLiked,
        isInCart = isInCart
    )
}

fun RankingCategoryVO.RankingCategoryItemsVO.PriceVO.toPresentation(): PriceModel {
    return PriceModel(
        discountPercent = discountPercent,
        discountedPrice = discountedPrice,
        originalPrice = originalPrice
    )
}