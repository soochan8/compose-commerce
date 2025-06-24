package com.chan.home.repository

import com.chan.home.vo.HomeSaleProductVO

interface HomeSaleProductRepository {
    suspend fun getSaleProducts(): List<HomeSaleProductVO>
}