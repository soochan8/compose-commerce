package com.chan.home.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chan.home.data.dao.HomeBannerDao
import com.chan.home.data.dao.HomePopularItemDao
import com.chan.home.data.dao.HomeSaleProductDao
import com.chan.home.data.dao.RankingCategoryDao
import com.chan.home.data.entity.home.HomeBannerEntity
import com.chan.home.data.entity.home.HomePopularItemEntity
import com.chan.home.data.entity.home.HomeSaleProductEntity
import com.chan.home.data.entity.ranking.RankingCategoryEntity

@Database(
    entities = [HomeBannerEntity::class, HomePopularItemEntity::class, RankingCategoryEntity::class, HomeSaleProductEntity::class],
    version = 5
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun homePopularItemDao(): HomePopularItemDao
    abstract fun rankingCategoryDao(): RankingCategoryDao
    abstract fun homeSaleProductDao(): HomeSaleProductDao
}