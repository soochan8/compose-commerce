package com.chan.database.entity.ranking

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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