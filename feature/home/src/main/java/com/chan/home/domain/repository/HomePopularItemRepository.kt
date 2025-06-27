package com.chan.home.domain.repository

import com.chan.home.domain.vo.HomePopularItemVO

interface HomePopularItemRepository {
    suspend fun getPopularItemAll(): List<HomePopularItemVO>
}