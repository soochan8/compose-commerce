package com.chan.login.domain.repository

interface LoginRepository {
    suspend fun registerUser(userId: String, userPw: String): Result<Unit>
    suspend fun appLogin(userId: String, userPw: String): Result<Unit>
}