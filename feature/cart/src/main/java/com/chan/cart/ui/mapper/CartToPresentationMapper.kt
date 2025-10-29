package com.chan.cart.ui.mapper

import com.chan.cart.model.CartInProductsModel
import com.chan.cart.model.PopupProductInfoModel
import com.chan.domain.CartProductVO
import com.chan.domain.ProductsVO
import java.text.NumberFormat
import java.util.Locale

fun ProductsVO.toPopupProductInfoModel(): PopupProductInfoModel {
    return PopupProductInfoModel(
        productId = productId,
        productName = productName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko"
    )
}

fun CartProductVO.toCartInProductsModel(): CartInProductsModel {
    return CartInProductsModel(
        productId = product.productId,
        productName = product.productName,
        brandName = product.brandName,
        imageUrl = "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/550/10/0000/0016/A00000016559829ko.jpg?l=ko",
        originalPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(product.originalPrice) + "원",
        discountPrice = product.discountPrice,
        discountPriceLabel = NumberFormat.getNumberInstance(Locale.KOREA).format(product.discountPrice) + "원",
        discountPercent = "${product.discountPercent}%",
        tags = product.tags,
        reviewRating = product.reviewRating.toString(),
        reviewCount = "(${NumberFormat.getNumberInstance(Locale.KOREA).format(product.reviewCount)})",
        categoryIds = product.categoryIds,
        isSelected = isSelected,
        quantity = quantity
    )
}