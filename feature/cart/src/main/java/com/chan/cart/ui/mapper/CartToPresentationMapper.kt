package com.chan.cart.ui.mapper

import com.chan.cart.model.CartInProductsModel
import com.chan.cart.model.PopupProductInfoModel
import com.chan.domain.ProductsVO

fun ProductsVO.toPopupProductInfoModel(): PopupProductInfoModel {
    return PopupProductInfoModel(
        productId = productId,
        productName = productName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0014/A00000014950135ko.jpg?l=ko"
    )
}

fun ProductsVO.toCartInProductsModel(): CartInProductsModel {
    return CartInProductsModel(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0014/A00000014950135ko.jpg?l=ko",
        originalPrice = originalPrice,
        discountPercent = discountPercent,
        discountPrice = discountPrice,
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = reviewCount,
        categoryIds = categoryIds
    )
}