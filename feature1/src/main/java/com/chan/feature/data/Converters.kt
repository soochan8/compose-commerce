package com.chan.feature.data

import androidx.room.TypeConverter
import com.chan.feature.data.entity.ranking.RankingCategoryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun itemsToJson(items: List<RankingCategoryEntity.RankingCategoryItems>): String =
        gson.toJson(items)

    @TypeConverter
    fun jsonToItems(json: String): List<RankingCategoryEntity.RankingCategoryItems> =
        gson.fromJson(json, object : TypeToken<List<RankingCategoryEntity.RankingCategoryItems>>() {}.type)
}