package com.chan.category.domian

import com.chan.category.domian.vo.CategoryVO

interface CategoryRepository {
    suspend fun getCategories() : List<CategoryVO>
}