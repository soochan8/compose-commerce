package com.chan.category.domain.vo


data class CategoryVO(
    val id: Int,
    val name: String,
    val subCategoryItems: List<SubCategoryVO>
) {
    data class SubCategoryVO(
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