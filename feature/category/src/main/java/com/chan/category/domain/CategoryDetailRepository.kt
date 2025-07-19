package com.chan.category.domain

import com.chan.category.domain.vo.ProductVO
import com.chan.category.domain.vo.detail.CategoryDetailTabsVO

interface CategoryDetailRepository {
    suspend fun getCategoryDetailTabs(subCategoryId: String): List<CategoryDetailTabsVO>
    suspend fun getCategoryDetailProducts(subCategoryId: String): List<ProductVO>
}