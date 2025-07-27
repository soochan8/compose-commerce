package com.chan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.UserDao
import com.chan.database.dao.ProductDetailDao
import com.chan.database.entity.ProductDetailEntity
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.UserEntity
import com.chan.database.entity.home.HomeBannerEntity

@Database(
    entities = [HomeBannerEntity::class, ProductEntity::class, ProductDetailEntity::class, UserEntity::class],
    version = 12,
    exportSchema = true
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun productDetailDao(): ProductDetailDao
}