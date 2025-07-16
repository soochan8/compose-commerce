package com.chan.category.data.mapper

import com.chan.category.domain.vo.ProductVO
import com.chan.category.domain.vo.detail.CategoryDetailTabsVO
import com.chan.database.dto.CategoryDetailTabsDto
import com.chan.database.entity.ProductEntity

fun CategoryDetailTabsDto.toTabsDomain(): CategoryDetailTabsVO {
    return CategoryDetailTabsVO(
        categoryId = categoryId,
        categoryName = categoryName,
    )
}

fun ProductEntity.Categories.SubCategories.Products.toDomain(): ProductVO {
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