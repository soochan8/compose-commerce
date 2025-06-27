package com.chan.home.data.dao

import com.chan.home.data.entity.ranking.RankingCategoryEntity

@androidx.room.Dao
interface RankingCategoryDao {
    @androidx.room.Query("SELECT * FROM rankingCategory")
    suspend fun getRankingCategoryAll(): List<RankingCategoryEntity>

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(banners: List<RankingCategoryEntity>)

    @androidx.room.Query("DELETE FROM rankingCategory")
    suspend fun deleteAll()
}