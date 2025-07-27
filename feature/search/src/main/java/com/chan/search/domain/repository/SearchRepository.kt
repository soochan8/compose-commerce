package com.chan.search.domain.repository

import com.chan.domain.ProductVO

interface SearchRepository {
    suspend fun searchProductName(search: String): List<ProductVO>
}