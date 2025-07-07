package com.chan.category.ui.model

data class CategoryModel(
    val id: Int,
    val name: String,
    val subCategoryItems: List<SubCategoryModel>
) {
    data class SubCategoryModel(
        val id: Int,
        val name: String,
        val items: List<SubCategoryItems>
    ) {
        data class SubCategoryItems(
            val id: Int,
            val name: String,
            val order: Int
        )
    }
}