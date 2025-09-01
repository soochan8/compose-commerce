package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.entity.CommonProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<CommonProductEntity>

    /**
     * categoryId가 "c1, c2, c3"형태로 구성
     * 특정 categoryId를 찾기 위해 분리
     */
    @Query(
        """
    SELECT * FROM products
    WHERE categoryIds LIKE :categoryId || ',%' 
       OR categoryIds LIKE '%,' || :categoryId || ',%' 
       OR categoryIds LIKE '%,' || :categoryId
       OR categoryIds = :categoryId
"""
    )
    suspend fun getProductsByCategoryId(categoryId: String): List<CommonProductEntity>

    @Query(
        """
    SELECT * FROM products
    WHERE categoryIds LIKE :categoryId || ',%' 
       OR categoryIds LIKE '%,' || :categoryId || ',%' 
       OR categoryIds LIKE '%,' || :categoryId
       OR categoryIds = :categoryId
       LIMIT :limit
"""
    )
    fun getProductsByCategoryIdLimit(categoryId: String, limit: Int = 5): Flow<List<CommonProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<CommonProductEntity>)

    //인기 상품
    @Query("SELECT * FROM products ORDER BY reviewCount DESC LIMIT :limit")
    fun getPopularProducts(limit: Int) : Flow<List<CommonProductEntity>>

    @Query("SELECT * FROM products WHERE productId = :productId")
    suspend fun getProductsByProductId(productId: String): CommonProductEntity
}