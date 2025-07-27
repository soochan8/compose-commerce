package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.entity.ProductDetailEntity

@Dao
interface ProductDetailDao {
    // 상품 상세 정보를 DB에 저장 (이미 있으면 덮어쓰기)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductDetail(productDetail: ProductDetailEntity)

    // productId로 특정 상품의 상세 정보를 가져오기
    @Query("SELECT * FROM productDetail WHERE productId = :productId")
    suspend fun getProductDetailById(productId: String): ProductDetailEntity
}