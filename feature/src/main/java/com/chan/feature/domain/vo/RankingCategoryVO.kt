package com.chan.feature.domain.vo

data class RankingCategoryVO(
    val rankingCategoryId: Int,
    val rankingCategoryName: String,
    val rankingCategoryItems: List<RankingCategoryItemsVO>
) {
    data class RankingCategoryItemsVO(
        val imageUrl: String,
        val productName: String,
        val price: PriceVO,
        val tags: List<String>,
        val isLiked: Boolean,
        val isInCart: Boolean
    ) {
        data class PriceVO(
            val discountPercent: Int?,
            val discountedPrice: Int,
            val originalPrice: Int,
        )
    }
}
