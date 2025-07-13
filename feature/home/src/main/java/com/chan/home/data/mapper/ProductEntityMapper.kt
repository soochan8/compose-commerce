package com.chan.home.data.mapper

import com.chan.database.entity.PriceEntity
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.home.HomeBannerEntity
import com.chan.database.entity.home.HomeSaleProductEntity
import com.chan.database.entity.ranking.RankingCategoryEntity
import com.chan.home.domain.vo.HomeBannerVO
import com.chan.home.domain.vo.HomeSaleProductVO
import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryVO
import com.chan.home.domain.vo.common.PriceVO

fun HomeBannerEntity.toDomain(): HomeBannerVO {
    return HomeBannerVO(
        id = id,
        imageUrl = imageUrl,
        title = title
    )
}

fun HomeSaleProductEntity.toDomain(): HomeSaleProductVO {
    return HomeSaleProductVO(
        id = id,
        imageUrl = imageUrl,
        productName = productName,
        price = price.toDomain(),
        tags = tags
    )
}

fun PriceEntity.toDomain(): PriceVO {
    return PriceVO(
        discountPercent = discountPercent,
        discountedPrice = discountedPrice,
        originalPrice = originalPrice
    )
}

fun ProductEntity.Products.toDomain(): ProductVO {
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

fun RankingCategoryEntity.toDomain(): RankingCategoryVO {
    return RankingCategoryVO(
        rankingCategoryId = rankingCategoryId,
        rankingCategoryName = rankingCategoryName,
        rankingCategoryItems = rankingCategoryItems.map { it.toDomain() }
    )
}

fun RankingCategoryEntity.RankingCategoryItems.toDomain(): RankingCategoryVO.RankingCategoryItemsVO {
    return RankingCategoryVO.RankingCategoryItemsVO(
        imageUrl = imageUrl,
        productName = productName,
        price = price.toDomain(),
        tags = tags,
        isLiked = isLiked,
        isInCart = isInCart
    )
}

fun RankingCategoryEntity.RankingCategoryItems.PriceDto.toDomain(): RankingCategoryVO.RankingCategoryItemsVO.PriceVO {
    return RankingCategoryVO.RankingCategoryItemsVO.PriceVO(
        discountPercent = discountPercent,
        discountedPrice = discountedPrice,
        originalPrice = originalPrice
    )
}

