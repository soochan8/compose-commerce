package com.chan.category.ui.model

data class CategoriesModel(
    val id: String,
    val name: String,
    val subCategories: List<CategoriesModel> = emptyList()
)