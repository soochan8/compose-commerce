package com.chan.cart.ui.mapper

import com.chan.cart.model.CartInProductsModel
import com.chan.cart.proto.CartItem

fun CartItem.toDataStoreCartInProductsModel(): CartInProductsModel {
    return CartInProductsModel(
        productId = this.productId,
        productName = this.productName,
        imageUrl = this.imageUrl,
        originalPrice = "%,d원".format(this.originPrice),
        discountPrice = this.discountedPrice,
        quantity = this.quantity,
        isSelected = this.isSelected,
        brandName = "",
        discountPercent = "",
        discountPriceLabel = "%,d원".format(this.discountedPrice),
        tags = emptyList(),
        reviewRating = "",
        reviewCount = "",
        categoryIds = emptyList()
    )
}

