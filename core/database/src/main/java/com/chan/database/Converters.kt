package com.chan.database

import androidx.room.TypeConverter
import com.chan.database.entity.ProductEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun subCategoriesToJson(products: List<ProductEntity.Categories>): String =
        gson.toJson(products)

    @TypeConverter
    fun jsonToCategories(json: String): List<ProductEntity.Categories> =
        gson.fromJson(
            json,
            object :
                TypeToken<List<ProductEntity.Categories>>() {}.type
        )
}