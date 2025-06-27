package com.chan.home

import androidx.room.TypeConverter
import com.chan.home.entity.ranking.RankingCategoryEntity
import com.chan.home.entity.ranking.RankingCategoryEntity.RankingCategoryItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        if (value.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}