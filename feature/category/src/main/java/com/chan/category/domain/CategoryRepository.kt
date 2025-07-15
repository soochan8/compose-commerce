package com.chan.category.domain

import com.chan.category.domain.dto.CategoriesDto

interface CategoryRepository {
    suspend fun getCategories() : List<CategoriesDto>
}