package com.chan.category.domain

import com.chan.category.domain.vo.detail.CategoryDetailNamesVO
import com.chan.category.domain.vo.detail.CategoryDetailVO

interface CategoryDetailRepository {
    suspend fun getCategoryNames(): List<CategoryDetailNamesVO>
    suspend fun getCategoryDetails(): List<CategoryDetailVO>

}