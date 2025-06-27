package com.chan.home.mapper

import com.chan.home.model.PriceModel
import com.chan.home.model.RankingCardModel
import com.chan.home.model.RankingCategoryModel
import com.chan.home.vo.RankingCategoryVO

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