package com.chan.category.ui.mapper

import com.chan.android.model.ProductModel
import com.chan.android.model.ProductsModel
import com.chan.category.domain.vo.ProductVO
import com.chan.category.domain.vo.detail.CategoryDetailTabsVO
import com.chan.category.ui.model.detail.CategoryDetailTabsModel
import com.chan.domain.ProductsVO
import java.text.NumberFormat
import java.util.Locale

//카테고리명
fun CategoryDetailTabsVO.toTabsModel(): CategoryDetailTabsModel {
    return CategoryDetailTabsModel(
        categoryId = categoryId,
        categoryName = categoryName
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
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(reviewCount)})",
    )
}

