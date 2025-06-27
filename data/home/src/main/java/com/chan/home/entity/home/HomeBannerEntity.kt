package com.chan.home.entity.home

import com.chan.home.domain.vo.HomeBannerVO

@androidx.room.Entity(tableName = "homeBanner")
data class HomeBannerEntity(
    @androidx.room.PrimaryKey val id: Int,
    val imageUrl: String,
    val title: String
)

fun HomeBannerEntity.toDomain(): com.chan.home.domain.vo.HomeBannerVO {
    return com.chan.home.domain.vo.HomeBannerVO(
        com.chan.home.domain.vo.HomeBannerVO.id = id,
        imageUrl = imageUrl,
        title = title
    )
}
