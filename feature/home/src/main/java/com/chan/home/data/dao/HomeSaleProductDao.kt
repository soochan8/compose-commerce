package com.chan.home.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.home.data.entity.home.HomeSaleProductEntity

@Dao
interface HomeSaleProductDao {
    @Query("SELECT * FROM HOMESALEPRODUCT")
    suspend fun getSaleProductAll(): List<HomeSaleProductEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(saleProducts: List<HomeSaleProductEntity>)

    @Query("DELETE FROM homeSaleProduct")
    suspend fun deleteAll()
}