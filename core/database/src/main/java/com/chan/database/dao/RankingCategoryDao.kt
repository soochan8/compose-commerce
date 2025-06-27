package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.entity.ranking.RankingCategoryEntity

@Dao
interface RankingCategoryDao {
    @Query("SELECT * FROM rankingCategory")
    suspend fun getRankingCategoryAll(): List<RankingCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(banners: List<RankingCategoryEntity>)

    @Query("DELETE FROM rankingCategory")
    suspend fun deleteAll()
}