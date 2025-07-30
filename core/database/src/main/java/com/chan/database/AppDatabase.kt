package com.chan.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.UserDao
import com.chan.database.dao.ProductDetailDao
import com.chan.database.dao.SearchHistoryDao
import com.chan.database.entity.ProductDetailEntity
import com.chan.database.entity.ProductEntity
import com.chan.database.entity.UserEntity
import com.chan.database.entity.home.HomeBannerEntity
import com.chan.database.entity.search.SearchHistoryEntity

val MIGRATION_13_14 = object : Migration(13, 14) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 1. 원하는 최종 스키마로 새로운 임시 테이블(_new)을 생성합니다.
        //    (PrimaryKey: search)
        db.execSQL("""
            CREATE TABLE `searchHistory_new` (
                `search` TEXT NOT NULL, 
                `timeStamp` INTEGER NOT NULL, 
                PRIMARY KEY(`search`)
            )
        """.trimIndent())

        // 2. 기존 테이블에서 새 테이블로 데이터를 복사합니다.
        //    GROUP BY를 사용해 중복된 search 값 중 가장 큰 timeStamp(가장 최신)를 가진 데이터만 선택합니다.
        db.execSQL("""
            INSERT INTO `searchHistory_new` (search, timeStamp)
            SELECT search, MAX(timeStamp) FROM `searchHistory` GROUP BY search
        """.trimIndent())

        // 3. 기존의 searchHistory 테이블을 삭제합니다.
        db.execSQL("DROP TABLE `searchHistory`")

        // 4. 새로 만든 임시 테이블의 이름을 원래 테이블 이름(searchHistory)으로 변경합니다.
        db.execSQL("ALTER TABLE `searchHistory_new` RENAME TO `searchHistory`")
    }
}

@Database(
    entities = [HomeBannerEntity::class, ProductEntity::class, ProductDetailEntity::class, UserEntity::class, SearchHistoryEntity::class],
    version = 14,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 12, to = 13)
    ]
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun productDetailDao(): ProductDetailDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}