package com.chan.home.dao

import com.chan.home.entity.home.HomePopularItemEntity

@androidx.room.Dao
interface HomePopularItemDao {
    @androidx.room.Query("SELECT * FROM homePopularItem")
    suspend fun getPopularItemAll(): List<HomePopularItemEntity>

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPopularItem(popularItems: List<HomePopularItemEntity>)

    @androidx.room.Query("DELETE FROM homePopularItem")
    suspend fun deleteAll()
}