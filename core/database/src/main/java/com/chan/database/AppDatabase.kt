package com.chan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.HomePopularItemDao
import com.chan.database.dao.HomeSaleProductDao
import com.chan.database.dao.RankingCategoryDao
import com.chan.database.dao.category.CategoryDao
import com.chan.database.entity.category.CategoryEntity
import com.chan.database.entity.home.HomeBannerEntity
import com.chan.database.entity.home.HomePopularItemEntity
import com.chan.database.entity.home.HomeSaleProductEntity
import com.chan.database.entity.ranking.RankingCategoryEntity

@Database(
    entities = [HomeBannerEntity::class, HomePopularItemEntity::class, RankingCategoryEntity::class, HomeSaleProductEntity::class, CategoryEntity::class],
    version = 6
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun homePopularItemDao(): HomePopularItemDao
    abstract fun rankingCategoryDao(): RankingCategoryDao
    abstract fun homeSaleProductDao(): HomeSaleProductDao
    abstract fun categoryDao(): CategoryDao
}