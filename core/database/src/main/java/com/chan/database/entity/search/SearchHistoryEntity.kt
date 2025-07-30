package com.chan.database.entity.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchHistory",)
data class SearchHistoryEntity(
    @PrimaryKey
    val search: String,
    val timeStamp: Long
)