package com.chan.category.data.datasource

import android.content.Context
import com.chan.database.entity.CommonCategoryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadCategoriesFromAsset() : List<CommonCategoryEntity> {
        return try {
            val jsonString = context.assets.open("category.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<CommonCategoryEntity>>() {}.type
            Gson().fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}