package com.chan.home.data.entity.ranking

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chan.home.domain.vo.RankingCategoryVO
import com.google.gson.annotations.SerializedName
import kotlin.collections.map


data class RankingCategoryWrapper(
    val categories: List<RankingCategoryEntity>
)

@Entity(tableName = "rankingCategory")
data class RankingCategoryEntity(
    @PrimaryKey
    @SerializedName("id")
    val rankingCategoryId: Int,
    @SerializedName("name")
    val rankingCategoryName: String,
    @SerializedName("rankingCategoryItems")
    val rankingCategoryItems: List<RankingCategoryItems>
) {
    data class RankingCategoryItems(
        val imageUrl: String,
        val productName: String,
        val price: PriceDto,
        val tags: List<String>,
        val isLiked: Boolean,
        val isInCart: Boolean
    ) {
        data class PriceDto(
            val discountPercent: Int?,
            val discountedPrice: Int,
            val originalPrice: Int,
        )
    }
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