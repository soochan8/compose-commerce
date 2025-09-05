package com.chan.database.dto

data class FilterCategoriesDto(
    val categoryId: String,
    val name: String,
    val subCategories: List<SubCategoryDto>
) {
    data class SubCategoryDto(
        val subCategoryId: String,
        val subCategoryName: String,
    )
}