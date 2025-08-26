package com.chan.search.ui.mappers

import com.chan.android.model.ProductModel
import com.chan.domain.ProductVO
import com.chan.domain.ProductsVO
import com.chan.search.domain.model.SearchHistoryVO
import com.chan.search.domain.model.TrendingSearchVO
import com.chan.search.ui.model.SearchHistoryModel
import com.chan.search.ui.model.SearchResultModel
import com.chan.search.ui.model.TrendingSearchModel
import java.text.NumberFormat
import java.util.Locale

fun ProductsVO.toSearchModel(): SearchResultModel {
    return SearchResultModel(
        productId = productId,
        productName = productName
    )
}

fun SearchHistoryVO.toSearchHistoryModel() : SearchHistoryModel {
    return SearchHistoryModel(
        search = search
    )
}

fun TrendingSearchVO.toTrendingSearchModel() : TrendingSearchModel {
    return TrendingSearchModel(
        rank = rank,
        keyword = keyword,
        change = change
    )
}

fun ProductVO.toProductsModel(): ProductModel {
    return ProductModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})"
    )
}

fun ProductsVO.toProductsModel(): ProductModel {
    return ProductModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(originalPrice) + "원",
        discountPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(discountPrice) + "원",
        discountPercent = "${discountPercent}%",
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})"
    )
}