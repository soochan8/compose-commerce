package com.chan.category.domain

import com.chan.category.domain.vo.CategoryVO

interface CategoryRepository {
    suspend fun getCategories() : List<CategoryVO>
}