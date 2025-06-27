package com.chan.home.entity.home

import com.chan.home.vo.HomePopularItemVO

@androidx.room.Entity(tableName = "homePopularItem")
data class HomePopularItemEntity(
    @androidx.room.PrimaryKey
    val id: Int,
    val imageUrl: String,
    val name: String,
    val originPrice: String,
    val discountedPrice: String,
    val discountPercent: String,
    val rating: String
)

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