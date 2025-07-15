package com.chan.category.ui.model


data class CategoryModel(
    val id: String,
    val name: String,
    val categories: List<Categories>
) {
    data class Categories(
        val id: String,
        val name: String,
        val subCategories: List<SubCategories>
    ) {
        data class SubCategories(
            val categoryId: String,
            val categoryName: String,
        )
    }
}