package com.chan.home.mapper

import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO
import com.chan.home.model.HomePopularItemModel
import com.chan.home.model.HomeRankingCategoryProductModel
import com.chan.home.model.HomeRankingCategoryTabModel
import com.chan.home.model.HomeSaleProductModel
import java.text.NumberFormat
import java.util.Locale

fun ProductVO.toPopularItemModel(): HomePopularItemModel {
    return HomePopularItemModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating.toString(),
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})"
    )
}


fun RankingCategoryTabVO.toRankingCategoryTabsModel(): HomeRankingCategoryTabModel {
    return HomeRankingCategoryTabModel(
        id = id,
        name = name
    )
}

fun ProductVO.toRankingCategoryProductModel(): HomeRankingCategoryProductModel {
    return HomeRankingCategoryProductModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0014/A00000014950135ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating.toString(),
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})"
    )
}

fun ProductVO.toSaleProductModel(): HomeSaleProductModel {
    return HomeSaleProductModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016134526ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating.toString(),
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})"
    )
}