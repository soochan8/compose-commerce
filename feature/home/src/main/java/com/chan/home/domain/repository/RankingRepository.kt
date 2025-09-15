package com.chan.home.domain.repository

import androidx.paging.PagingData
import com.chan.domain.ProductsVO
import kotlinx.coroutines.flow.Flow

interface RankingRepository {
    fun getAllProducts() : Flow<PagingData<ProductsVO>>
}