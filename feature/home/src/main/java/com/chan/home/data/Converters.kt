package com.chan.home.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun itemsToJson(items: List<com.chan.home.data.entity.ranking.RankingCategoryEntity.RankingCategoryItems>): String =
        gson.toJson(items)

    @TypeConverter
    fun jsonToItems(json: String): List<com.chan.home.data.entity.ranking.RankingCategoryEntity.RankingCategoryItems> =
        gson.fromJson(
            json,
            object :
                com.google.gson.reflect.TypeToken<List<com.chan.home.data.entity.ranking.RankingCategoryEntity.RankingCategoryItems>>() {}.type
        )

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value.isNullOrEmpty()) return emptyList()
        val type = object : com.google.gson.reflect.TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}