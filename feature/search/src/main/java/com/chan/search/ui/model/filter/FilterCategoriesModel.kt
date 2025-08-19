package com.chan.search.ui.model.filter

data class FilterCategoriesModel(
    val categoryId: String,
    val name: String,
    val subCategories: List<SubCategoryUiModel>
) {

    data class SubCategoryUiModel(
        val subCategoryId: String,
        val subCategoryName: String,
    )
}
