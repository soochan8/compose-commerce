package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.entity.CommonCategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<CommonCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAllCategories(categories: List<CommonCategoryEntity>)

    @Query("""
    SELECT * 
    FROM category
    WHERE parentCategoryId = (
        SELECT parentCategoryId 
        FROM category 
        WHERE id = :categoryId
    )
""")
    suspend fun getParentCategoryByCategoryId(categoryId: String): List<CommonCategoryEntity>

}