package com.chan.search.data.mappers

import com.chan.android.model.ProductModel
import com.chan.database.dto.FilterCategoriesDto
import com.chan.database.entity.CommonProductEntity
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.search.SearchHistoryEntity
import com.chan.domain.ProductVO
import com.chan.domain.ProductsVO
import com.chan.search.domain.model.FilterCategoriesVO
import com.chan.search.domain.model.SearchHistoryVO
import java.text.NumberFormat
import java.util.Locale

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

fun SearchHistoryEntity.toDomain(): SearchHistoryVO {
    return SearchHistoryVO(
        search = search,
        timeStamp = timeStamp
    )
}

fun SearchHistoryVO.toSearchHistoryEntity(): SearchHistoryEntity {
    return SearchHistoryEntity(
        search = search,
        timeStamp = timeStamp
    )
}

fun CommonProductEntity.toProductsVO(): ProductsVO {
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
        categoryIds = categoryIds,
    )
}