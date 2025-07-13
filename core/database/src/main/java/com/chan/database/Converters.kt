package com.chan.database

import androidx.room.TypeConverter
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.ranking.RankingCategoryEntity.RankingCategoryItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.text.isNullOrEmpty

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun subCategoriesToJson(products: List<ProductEntity.SubCategories>): String =
        gson.toJson(products)

    @TypeConverter
    fun jsonToSubCategories(json: String): List<ProductEntity.SubCategories> =
        gson.fromJson(
            json,
            object :
                TypeToken<List<ProductEntity.SubCategories>>() {}.type
        )

    @TypeConverter
    fun itemsToJson(items: List<RankingCategoryItems>): String =
        gson.toJson(items)

    @TypeConverter
    fun jsonToItems(json: String): List<RankingCategoryItems> =
        gson.fromJson(
            json,
            object :
                TypeToken<List<RankingCategoryItems>>() {}.type
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