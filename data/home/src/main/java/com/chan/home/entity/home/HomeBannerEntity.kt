package com.chan.home.entity.home

import com.chan.home.vo.HomeBannerVO

@androidx.room.Entity(tableName = "homeBanner")
data class HomeBannerEntity(
    @androidx.room.PrimaryKey val id: Int,
    val imageUrl: String,
    val title: String
)

fun HomeBannerEntity.toDomain(): HomeBannerVO {
    return HomeBannerVO(
        id = id,
        imageUrl = imageUrl,
        title = title
    )
}
