package com.chan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chan.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun findByUsername(userId: String): UserEntity?
}