package com.chan.search.data.mappers

import com.chan.database.entity.ProductEntity
import com.chan.database.entity.search.SearchHistoryEntity
import com.chan.domain.ProductVO
import com.chan.search.domain.model.SearchHistoryVO
import com.chan.search.ui.model.SearchHistoryModel

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