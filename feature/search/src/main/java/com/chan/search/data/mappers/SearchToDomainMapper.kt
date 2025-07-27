package com.chan.search.data.mappers

import com.chan.database.entity.ProductEntity
import com.chan.domain.ProductVO

fun ProductEntity.Categories.SubCategories.Products.toDomain() : ProductVO {
    return ProductVO(
        productId = productId,
        productName = productName,
        brandName = brandName,
        imageUrl = imageUrl,
        originalPrice = originalPrice,
        discountPercent = discountPercent,
        discountPrice = discountPrice,
        tags = tags,
        reviewRating = reviewRating,
        reviewCount = reviewCount
    )
}