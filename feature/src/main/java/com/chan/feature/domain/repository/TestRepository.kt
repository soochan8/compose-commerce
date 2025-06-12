package com.chan.feature.domain.repository

import com.chan.feature.domain.vo.UserVO

interface TestRepository {
    suspend fun getUser() : UserVO
}