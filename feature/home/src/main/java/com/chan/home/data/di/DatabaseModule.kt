package com.chan.home.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.chan.home.data.dao.HomeBannerDao
import com.chan.home.data.dao.HomePopularItemDao
import com.chan.home.data.dao.HomeSaleProductDao
import com.chan.home.data.dao.RankingCategoryDao
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
    fun provideDatabase(@ApplicationContext context: Context): com.chan.home.data.AppDatabase {
        return databaseBuilder(
            context,
            com.chan.home.data.AppDatabase::class.java,
            "commerce_clone_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHomeBannerDao(db: com.chan.home.data.AppDatabase): HomeBannerDao = db.homeBannerDao()

    @Provides
    fun provideHomePopularItemDao(db: com.chan.home.data.AppDatabase): HomePopularItemDao = db.homePopularItemDao()

    @Provides
    fun provideRankingCategoryDao(db: com.chan.home.data.AppDatabase): RankingCategoryDao = db.rankingCategoryDao()

    @Provides
    fun provideHomeSaleProductDao(db: com.chan.home.data.AppDatabase): HomeSaleProductDao = db.homeSaleProductDao()
}