package com.chan.category.data.datasource

import android.content.Context
import com.chan.category.data.dto.CategoryDetailDto
import com.chan.category.data.dto.CategoryDetailNamesDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CategoryDetailRemoteSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getCategoryNames(): List<CategoryDetailNamesDto> {
        return try {
            val jsonString = context.assets.open("category_detail_names.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<CategoryDetailNamesDto>>() {}.type
            return Gson().fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getCategoryDetails(): List<CategoryDetailDto> {
        return try {
            val jsonString = context.assets.open("category_detail_list.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<CategoryDetailDto>>() {}.type
            return Gson().fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

