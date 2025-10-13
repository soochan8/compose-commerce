package com.chan.database.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homeBanner")
data class HomeBannerEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val linkType: String,
    val linkUrl: String
)