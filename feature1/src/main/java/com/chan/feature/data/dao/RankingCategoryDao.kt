package com.chan.feature.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.feature.data.entity.home.HomeBannerEntity
import com.chan.feature.data.entity.ranking.RankingCategoryEntity

@Dao
interface RankingCategoryDao {
    @Query("SELECT * FROM rankingCategory")
    suspend fun getRankingCategoryAll() : List<RankingCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(banners: List<RankingCategoryEntity>)

    @Query("DELETE FROM rankingCategory")
    suspend fun deleteAll()
}