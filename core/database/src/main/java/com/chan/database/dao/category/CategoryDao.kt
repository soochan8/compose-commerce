package com.chan.database.dao.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.entity.category.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categoryTable")
    suspend fun getBannerAll(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Query("DELETE FROM categoryTable")
    suspend fun deleteAll()
}