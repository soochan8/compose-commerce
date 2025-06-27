package com.chan.home

import com.chan.home.entity.ranking.RankingCategoryEntity
import com.chan.home.entity.ranking.RankingCategoryEntity.RankingCategoryItems
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @androidx.room.TypeConverter
    fun itemsToJson(items: List<RankingCategoryItems>): String =
        gson.toJson(items)

    @androidx.room.TypeConverter
    fun jsonToItems(json: String): List<RankingCategoryItems> =
        gson.fromJson(json,
            object :
                com.google.gson.reflect.TypeToken<List<RankingCategoryItems>>() {}.type
        )
}