package com.chan.cart.data.mapper

import com.chan.database.dto.ProductWithCartDto
import com.chan.database.entity.CommonProductEntity
import com.chan.domain.CartProductVO
import com.chan.domain.ProductsVO

fun CommonProductEntity.toProductsVO() : ProductsVO {
    return ProductsVO(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = imageUrl,
        originalPrice = originalPrice,
        discountPercent = discountPercent,
        discountPrice = discountPrice,
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = reviewCount,
        categoryIds = categoryIds
    )
}

fun ProductWithCartDto.toCartProductVO(): CartProductVO {
    return CartProductVO(
        product = this.product.toProductsVO(),
        quantity = this.cartQuantity,
        isSelected = this.isSelected
    )
}