package com.chan.database.dao.category

import androidx.room.TypeConverter
import com.chan.database.entity.category.CategoryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryConverters {
    private val gson = Gson()

    @TypeConverter
    fun itemsToJson(items: List<CategoryEntity.SubCategoryEntity>): String =
        gson.toJson(items)

    @TypeConverter
    fun jsonToItems(json: String): List<CategoryEntity.SubCategoryEntity> =
        gson.fromJson(
            json,
            object :
                TypeToken<List<CategoryEntity.SubCategoryEntity>>() {}.type
        )
}