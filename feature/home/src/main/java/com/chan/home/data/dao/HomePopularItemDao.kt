package com.chan.home.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.home.data.entity.home.HomePopularItemEntity

@Dao
interface HomePopularItemDao {
    @Query("SELECT * FROM homePopularItem")
    suspend fun getPopularItemAll(): List<HomePopularItemEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPopularItem(popularItems: List<HomePopularItemEntity>)

    @Query("DELETE FROM homePopularItem")
    suspend fun deleteAll()
}