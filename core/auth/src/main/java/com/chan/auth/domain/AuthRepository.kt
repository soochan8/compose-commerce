package com.chan.auth.domain

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getSessionFlow(): Flow<UserSession?>
    suspend fun login(userId: String, token: String)
    suspend fun logout()
}

data class UserSession(val userId: String, val token: String)