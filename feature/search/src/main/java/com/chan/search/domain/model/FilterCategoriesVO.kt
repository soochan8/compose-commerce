package com.chan.search.domain.model

data class FilterCategoriesVO(
    val categoryId: String,
    val name: String,
    val subCategories: List<SubCategoryVO>
)

data class SubCategoryVO(
    val subCategoryId: String,
    val subCategoryName: String,
)