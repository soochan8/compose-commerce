package com.chan.database.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.chan.database.entity.CommonProductEntity

data class ProductWithCartDto(
    @Embedded val product: CommonProductEntity,
    @ColumnInfo(name = "cartQuantity") val cartQuantity: Int,
    @ColumnInfo(name = "isSelected") val isSelected: Boolean
)
