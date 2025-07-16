package com.chan.category.ui.mapper

import com.chan.category.domain.dto.CategoriesDto
import com.chan.category.ui.model.CategoryModel

fun CategoriesDto.toCategoryModel(): CategoryModel {
    return CategoryModel(
        id = id,
        name = name,
        categories = categories.map { it.toCategoryModel() }
    )
}

fun CategoriesDto.Categories.toCategoryModel(): CategoryModel.Categories {
    return CategoryModel.Categories(
        id = id,
        name = name,
        subCategories = subCategories.map { it.toCategoryModel() }
    )
}

fun CategoriesDto.Categories.SubCategories.toCategoryModel(): CategoryModel.Categories.SubCategories {
    return CategoryModel.Categories.SubCategories(
        categoryId = categoryId,
        categoryName = categoryName
    )
}