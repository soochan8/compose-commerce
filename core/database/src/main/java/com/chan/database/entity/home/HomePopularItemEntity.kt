package com.chan.database.entity.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homePopularItem")
data class HomePopularItemEntity(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val name: String,
    val originPrice: String,
    val discountedPrice: String,
    val discountPercent: String,
    val rating: String
)