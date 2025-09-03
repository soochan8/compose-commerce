package com.chan.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chan.database.dao.CategoryDao
import com.chan.database.dao.HomeBannerDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.ProductDetailDao
import com.chan.database.dao.ProductsDao
import com.chan.database.dao.SearchHistoryDao
import com.chan.database.dao.UserDao
import com.chan.database.entity.CommonCategoryEntity
import com.chan.database.entity.CommonProductEntity
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
        productDao: Provider<ProductDao>,
        productsDao: Provider<ProductsDao>,
        categoryDao: Provider<CategoryDao>
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
                        insertAllProducts(context, productsDao.get())
                        insertAllCategory(context, categoryDao.get())
                        products(context, productDao.get())
                    }
                }
            }
        )
            .addMigrations(MIGRATION_13_14)
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

    private suspend fun insertAllProducts(context: Context, productsDao: ProductsDao) {
        val fileName = "product.json"
        val jsonString = context.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }

        val listType = object : TypeToken<List<CommonProductEntity>>() {}.type
        val products: List<CommonProductEntity> = Gson().fromJson(jsonString, listType)
        productsDao.insertAllProducts(products)
    }

    private suspend fun insertAllCategory(context: Context, categoryDao: CategoryDao) {
        val fileName = "category.json"
        val jsonString = context.assets
            .open(fileName)
            .bufferedReader()
            .use { it.readText() }

        val listType = object : TypeToken<List<CommonCategoryEntity>>() {}.type
        val category: List<CommonCategoryEntity> = Gson().fromJson(jsonString, listType)
        categoryDao.insertAllCategories(category)
    }

    @Provides
    fun provideHomeBannerDao(db: AppDatabase): HomeBannerDao =
        db.homeBannerDao()

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao =
        db.productDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao =
        db.userDao()

    @Provides
    fun provideProductDetailDao(db: AppDatabase): ProductDetailDao =
        db.productDetailDao()

    @Provides
    fun provideSearchHistoryDao(db: AppDatabase): SearchHistoryDao =
        db.searchHistoryDao()

    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao =
        db.categoryDao()

    @Provides
    fun provideProductsDao(db: AppDatabase): ProductsDao =
        db.productsDao()
}