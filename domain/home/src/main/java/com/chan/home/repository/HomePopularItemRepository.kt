package com.chan.home.repository

import com.chan.home.vo.HomePopularItemVO

interface HomePopularItemRepository {
    suspend fun getPopularItemAll(): List<HomePopularItemVO>
}