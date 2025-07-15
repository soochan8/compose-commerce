package com.chan.category.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDto(
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