package com.chan.category.data.datasource

import android.content.Context
import com.chan.database.entity.category.CategoryEntity
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getRankingCategories(): List<CategoryEntity> {
        return try {
            // 1) 파일 전체 읽기
            val jsonString = context.assets.open("category_data.json")
                .bufferedReader()
                .use { it.readText() }

            // 2) 루트 객체로 파싱
            val rootObj = JsonParser.parseString(jsonString).asJsonObject

            // 3) "categories" 배열만 꺼내기
            val categoriesArray = rootObj.getAsJsonArray("categories")

            // 4) BEGIN_ARRAY 기대하도록 TypeToken 설정
            val type = object : TypeToken<List<CategoryEntity>>() {}.type

            // 5) JsonArray → List<RankingCategoryEntity>
            val list: List<CategoryEntity> =
                Gson().fromJson(categoriesArray, type)

            list
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}