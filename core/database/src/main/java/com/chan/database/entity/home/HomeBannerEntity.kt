package com.chan.database.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homeBanner")
data class HomeBannerEntity(
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val title: String
)