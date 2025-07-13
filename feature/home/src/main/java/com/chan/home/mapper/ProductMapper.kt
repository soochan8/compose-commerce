package com.chan.home.mapper

import com.chan.home.domain.vo.ProductVO
import com.chan.home.model.HomePopularItemModel
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