package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.entity.home.HomeSaleProductEntity

@Dao
interface HomeSaleProductDao {
    @Query("SELECT * FROM HOMESALEPRODUCT")
    suspend fun getSaleProductAll(): List<HomeSaleProductEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(saleProducts: List<HomeSaleProductEntity>)

    @Query("DELETE FROM homeSaleProduct")
    suspend fun deleteAll()
}