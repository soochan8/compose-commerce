package com.chan.home.data.mapper

import com.chan.database.dto.CategoryTabDto
import com.chan.database.entity.CommonCategoryEntity
import com.chan.database.entity.CommonProductEntity
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.home.HomeBannerEntity
import com.chan.domain.ProductsVO
import com.chan.home.domain.vo.HomeBannerVO
import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO

fun HomeBannerEntity.toDomain(): HomeBannerVO {
    return HomeBannerVO(
        id = id,
        imageUrl = imageUrl,
        linkType = linkType,
        linkUrl = linkUrl
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
        categoryIds = categoryIds
    )
}

fun CategoryTabDto.toDomain() : RankingCategoryTabVO {
    return RankingCategoryTabVO(
        id = id,
        name = name
    )
}

fun CommonCategoryEntity.toRankingCategoryTabVO() : RankingCategoryTabVO {
    return RankingCategoryTabVO(
        id = id,
        name = name
    )
}






