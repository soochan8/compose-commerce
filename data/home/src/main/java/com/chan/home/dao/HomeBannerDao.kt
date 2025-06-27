package com.chan.home.dao

import com.chan.home.entity.home.HomeBannerEntity

@androidx.room.Dao
interface HomeBannerDao {

    @androidx.room.Query("SELECT * FROM homeBanner")
    suspend fun getBannerAll(): List<HomeBannerEntity>

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(banners: List<HomeBannerEntity>)

    @androidx.room.Query("DELETE FROM homeBanner")
    suspend fun deleteAll()
}