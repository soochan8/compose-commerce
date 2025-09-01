package com.chan.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chan.database.dao.CartDao
import com.chan.database.dao.CategoryDao
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.ProductDetailDao
import com.chan.database.dao.ProductsDao
import com.chan.database.dao.SearchHistoryDao
import com.chan.database.dao.UserDao
import com.chan.database.entity.CommonCategoryEntity
import com.chan.database.entity.CommonProductEntity
import com.chan.database.entity.ProductDetailEntity
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.UserEntity
import com.chan.database.entity.cart.CartProductEntity
import com.chan.database.entity.home.HomeBannerEntity
import com.chan.database.entity.search.SearchHistoryEntity

val MIGRATION_13_14 = object : Migration(13, 14) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE `searchHistory_new` (
                `search` TEXT NOT NULL, 
                `timeStamp` INTEGER NOT NULL, 
                PRIMARY KEY(`search`)
            )
        """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO `searchHistory_new` (search, timeStamp)
            SELECT search, MAX(timeStamp) FROM `searchHistory` GROUP BY search
        """.trimIndent()
        )

        db.execSQL("DROP TABLE `searchHistory`")

        db.execSQL("ALTER TABLE `searchHistory_new` RENAME TO `searchHistory`")
    }
}

val MIGRATION_15_16 = object : Migration(15, 16) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `cart_products` (
                `userId` TEXT NOT NULL,
                `productId` TEXT NOT NULL,
                `quantity` INTEGER NOT NULL,
                PRIMARY KEY(`userId`, `productId`)
            )
        """.trimIndent()
        )
    }
}

@Database(
    entities = [HomeBannerEntity::class, ProductEntity::class, ProductDetailEntity::class, UserEntity::class, SearchHistoryEntity::class, CommonProductEntity::class, CommonCategoryEntity::class, CartProductEntity::class],
    version = 16,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 12, to = 13)
    ]
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun productDetailDao(): ProductDetailDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productsDao(): ProductsDao
    abstract fun cartDao(): CartDao
}