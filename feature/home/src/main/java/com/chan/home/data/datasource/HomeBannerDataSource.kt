package com.chan.home.data.datasource

import android.content.Context
import com.chan.database.entity.home.HomeBannerEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.io.bufferedReader
import kotlin.io.readText
import kotlin.io.use

class HomeBannerDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getDummyHomeBanners(): List<HomeBannerEntity> {
        return try {
            val jsonString = context.assets.open("home_banner.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<HomeBannerEntity>>() {}.type
            return Gson().fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}