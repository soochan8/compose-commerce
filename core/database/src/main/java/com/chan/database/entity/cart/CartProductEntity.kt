package com.chan.database.entity.cart

import androidx.room.Entity

@Entity(
    tableName = "cart_products",
    primaryKeys = ["userId", "productId"]
)
data class CartProductEntity(
    val userId: String,
    val productId: String,
    val quantity: Int,
    val isSelected: Boolean = true
)