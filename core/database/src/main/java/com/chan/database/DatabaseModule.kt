package com.chan.database

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.HomePopularItemDao
import com.chan.database.dao.HomeSaleProductDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.RankingCategoryDao
import com.chan.database.dao.category.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return databaseBuilder(
            context,
            AppDatabase::class.java,
            "commerce_clone_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHomeBannerDao(db: AppDatabase): HomeBannerDao =
        db.homeBannerDao()

    @Provides
    fun provideHomePopularItemDao(db: AppDatabase): HomePopularItemDao =
        db.homePopularItemDao()

    @Provides
    fun provideRankingCategoryDao(db: AppDatabase): RankingCategoryDao =
        db.rankingCategoryDao()

    @Provides
    fun provideHomeSaleProductDao(db: AppDatabase): HomeSaleProductDao =
        db.homeSaleProductDao()

    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao =
        db.categoryDao()

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao =
        db.productDao()
}