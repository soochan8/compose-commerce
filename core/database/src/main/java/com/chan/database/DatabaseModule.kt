package com.chan.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.HomeSaleProductDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.RankingCategoryDao
import com.chan.database.dao.category.CategoryDao
import com.chan.database.entity.ProductEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        productDao: Provider<ProductDao>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "commerce_clone_db"
        ).addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        products(context, productDao.get())
                    }
                }
            }
        ).fallbackToDestructiveMigration()
            .build()
    }

    private suspend fun products(context: Context, productDao: ProductDao) {
        val fileName = "product_list.json"
        val jsonString = context.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }

        val listType = object : TypeToken<List<ProductEntity>>() {}.type
        val products: List<ProductEntity> = Gson().fromJson(jsonString, listType)
        productDao.insertAll(products)
    }

    @Provides
    fun provideHomeBannerDao(db: AppDatabase): HomeBannerDao =
        db.homeBannerDao()

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