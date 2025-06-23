package com.chan.home.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.chan.home.AppDatabase
import com.chan.home.dao.HomeBannerDao
import com.chan.home.dao.HomePopularItemDao
import com.chan.home.dao.RankingCategoryDao
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
    fun provideHomeBannerDao(db: AppDatabase): HomeBannerDao = db.homeBannerDao()

    @Provides
    fun provideHomePopularItemDao(db: AppDatabase): HomePopularItemDao = db.homePopularItemDao()

    @Provides
    fun provideRankingCategoryDao(db: AppDatabase): RankingCategoryDao = db.rankingCategoryDao()
}