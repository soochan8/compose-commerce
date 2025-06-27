package com.chan.home.domain.repository

import com.chan.home.domain.vo.HomeSaleProductVO

interface HomeSaleProductRepository {
    suspend fun getSaleProducts(): List<HomeSaleProductVO>
}