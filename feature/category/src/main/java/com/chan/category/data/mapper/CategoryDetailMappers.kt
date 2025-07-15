package com.chan.category.data.mapper

import com.chan.category.data.dto.CategoryDetailDto
import com.chan.category.data.dto.CategoryDetailNamesDto
import com.chan.category.domain.vo.detail.CategoryDetailNamesVO
import com.chan.category.domain.vo.detail.CategoryDetailVO
import com.chan.category.domain.vo.detail.PriceVO
import com.chan.category.domain.vo.detail.ReviewVO

//카테고리명
fun CategoryDetailNamesDto.toDomain(): CategoryDetailNamesVO {
    return CategoryDetailNamesVO(
        id = id,
        name = name,
        sortOrder = sortOrder
    )
}

//리스트
fun CategoryDetailDto.toDomain(): CategoryDetailVO {
    return CategoryDetailVO(
        productId = productId,
        productName = productName,
        imageUrl = imageUrl,
        priceVO = PriceVO(
            originPrice = price.originPrice,
            discountedPrice = price.discountedPrice,
            discountPercent = price.discountPercent
        ),
        reviewVO = ReviewVO(
            rating = review.rating,
            reviewCount = review.reviewCount
        )
    )
}