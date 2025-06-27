package com.chan.database.entity.home

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chan.database.entity.PriceEntity

@Entity(tableName = "homeSaleProduct")
data class HomeSaleProductEntity(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val productName: String,
    @Embedded val price: PriceEntity,
    val tags: List<String>? = null
)