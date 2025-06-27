package com.chan.home.data.mapper

import com.chan.database.entity.PriceEntity
import com.chan.database.entity.home.HomeBannerEntity
import com.chan.database.entity.home.HomePopularItemEntity
import com.chan.database.entity.home.HomeSaleProductEntity
import com.chan.database.entity.ranking.RankingCategoryEntity
import com.chan.home.domain.vo.HomeBannerVO
import com.chan.home.domain.vo.HomePopularItemVO
import com.chan.home.domain.vo.HomeSaleProductVO
import com.chan.home.domain.vo.RankingCategoryVO
import com.chan.home.domain.vo.common.PriceVO

fun HomeBannerEntity.toDomain(): HomeBannerVO {
    return HomeBannerVO(
        id = id,
        imageUrl = imageUrl,
        title = title
    )
}

fun HomePopularItemEntity.toDomain(): HomePopularItemVO {
    return HomePopularItemVO(
        id = id,
        imageUrl = imageUrl,
        name = name,
        originPrice = originPrice,
        discountedPrice = discountedPrice,
        discountPercent = discountPercent,
        rating = rating
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

