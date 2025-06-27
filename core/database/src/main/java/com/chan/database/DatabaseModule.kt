package com.chan.database

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.HomePopularItemDao
import com.chan.database.dao.HomeSaleProductDao
import com.chan.database.dao.RankingCategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(dagger.hilt.components.SingletonComponent::class)
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

    @dagger.Provides
    fun provideHomeBannerDao(db: AppDatabase): HomeBannerDao =
        db.homeBannerDao()

    @dagger.Provides
    fun provideHomePopularItemDao(db: AppDatabase): HomePopularItemDao =
        db.homePopularItemDao()

    @dagger.Provides
    fun provideRankingCategoryDao(db: AppDatabase): RankingCategoryDao =
        db.rankingCategoryDao()

    @dagger.Provides
    fun provideHomeSaleProductDao(db: AppDatabase): HomeSaleProductDao =
        db.homeSaleProductDao()
}