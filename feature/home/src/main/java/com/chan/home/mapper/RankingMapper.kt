package com.chan.home.mapper

import com.chan.home.domain.vo.RankingCategoryVO
import com.chan.home.domain.vo.common.PriceVO
import com.chan.home.model.PriceModel
import com.chan.home.model.RankingCardModel
import com.chan.home.model.RankingCategoryModel

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

fun PriceVO.toPresentation(): PriceModel {
    return PriceModel(
        discountPercent = discountPercent,
        discountedPrice = discountedPrice,
        originalPrice = originalPrice
    )
}