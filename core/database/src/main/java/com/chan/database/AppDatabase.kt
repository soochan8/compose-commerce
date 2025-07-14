package com.chan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.category.CategoryDao
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.category.CategoryEntity
import com.chan.database.entity.home.HomeBannerEntity

@Database(
    entities = [HomeBannerEntity::class, CategoryEntity::class, ProductEntity::class],
    version = 11
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
}