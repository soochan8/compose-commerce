package com.chan.category.domian

import com.chan.category.domian.vo.detail.CategoryDetailNamesVO
import com.chan.category.domian.vo.detail.CategoryDetailVO

interface CategoryDetailRepository {
    suspend fun getCategoryNames(): List<CategoryDetailNamesVO>
    suspend fun getCategoryDetails(): List<CategoryDetailVO>

}