package com.chan.feature.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chan.feature.data.dao.HomeBannerDao
import com.chan.feature.data.dao.HomePopularItemDao
import com.chan.feature.data.dao.RankingCategoryDao
import com.chan.feature.data.entity.home.HomeBannerEntity
import com.chan.feature.data.entity.home.HomePopularItemEntity
import com.chan.feature.data.entity.ranking.RankingCategoryEntity

@Database(
    entities = [HomeBannerEntity::class, HomePopularItemEntity::class, RankingCategoryEntity::class],
    version = 4
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun homePopularItemDao(): HomePopularItemDao
    abstract fun rankingCategoryDao(): RankingCategoryDao
}