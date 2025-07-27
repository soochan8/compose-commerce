package com.chan.login.domain.repository

import com.chan.login.domain.vo.UserVO

interface LoginRepository {
    suspend fun registerUser(user: UserVO)
    suspend fun findUserByUserId(userId: String): UserVO?
}