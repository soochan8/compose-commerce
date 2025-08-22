package com.chan.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CommonCategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val parentCategoryId: String?
)
