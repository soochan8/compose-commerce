package com.chan.category.domain

import com.chan.category.domain.vo.ProductVO
import com.chan.category.domain.vo.detail.CategoryDetailTabsVO
import com.chan.domain.ProductsVO

interface CategoryDetailRepository {
    suspend fun getCategoryTabs(categoryId: String): List<CategoryDetailTabsVO>
    suspend fun getProductsByCategory(categoryId: String): List<ProductsVO>
    suspend fun getProductsAll(): List<ProductsVO>
}