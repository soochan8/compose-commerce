package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chan.database.entity.search.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    // 최근 검색어를 시간순으로 정렬
    @Query("SELECT * FROM searchHistory ORDER BY timestamp DESC")
    fun getRecentSearches(): Flow<List<SearchHistoryEntity>>

    // 검색어 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchHistory: SearchHistoryEntity)

    // 특정 검색어 삭제
    @Query("DELETE FROM searchHistory WHERE search = :search")
    suspend fun deleteSearch(search: String)

    // 전체 검색 기록 삭제
    @Query("DELETE FROM searchHistory")
    suspend fun clearAll()
}