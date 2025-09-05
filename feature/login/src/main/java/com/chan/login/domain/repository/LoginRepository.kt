package com.chan.login.domain.repository

import com.chan.login.domain.vo.UserVO

interface LoginRepository {
    suspend fun registerUser(userId: String, userPw: String): Result<Unit>
    suspend fun appLogin(userId: String, userPw: String): UserVO
}