package com.chan.home

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chan.home.dao.HomeBannerDao
import com.chan.home.dao.HomePopularItemDao
import com.chan.home.dao.RankingCategoryDao
import com.chan.home.entity.home.HomeBannerEntity
import com.chan.home.entity.home.HomePopularItemEntity
import com.chan.home.entity.ranking.RankingCategoryEntity

@androidx.room.Database(
    entities = [HomeBannerEntity::class, HomePopularItemEntity::class, RankingCategoryEntity::class],
    version = 4
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun homePopularItemDao(): HomePopularItemDao
    abstract fun rankingCategoryDao(): RankingCategoryDao
}