package com.chan.category.ui.mapper

import com.chan.android.model.Price
import com.chan.android.model.ProductModel
import com.chan.android.model.Review
import com.chan.category.domain.vo.detail.CategoryDetailNamesVO
import com.chan.category.domain.vo.detail.CategoryDetailVO
import com.chan.category.domain.vo.detail.PriceVO
import com.chan.category.domain.vo.detail.ReviewVO
import com.chan.category.ui.model.detail.CategoryNamesModel

//카테고리명
fun CategoryDetailNamesVO.toPresentation(): CategoryNamesModel {
    return CategoryNamesModel(
        id = id,
        name = name,
        sortOrder = sortOrder,
    )
}

//리스트
fun CategoryDetailVO.toPresentationModel(): ProductModel {
    return ProductModel(
        productId = productId,
        productName = productName,
        imageUrl = imageUrl,
        price = priceVO.toPresentationModel(),
        review = reviewVO?.toPresentation()
    )
}

fun PriceVO.toPresentationModel(): Price {
    return Price(
        originPrice = originPrice,
        discountedPrice = discountedPrice,
        discountPercent = discountPercent
    )
}

fun ReviewVO.toPresentation(): Review {
    return Review(
        rating = rating,
        reviewCount = reviewCount
    )
}
