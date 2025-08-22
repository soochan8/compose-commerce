package com.chan.category.ui.mapper

import com.chan.category.domain.dto.CategoriesDto
import com.chan.category.domain.vo.CategoryVO
import com.chan.category.ui.model.CategoriesModel
import com.chan.category.ui.model.CategoryModel
import com.chan.database.entity.CommonCategoryEntity

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

fun List<CategoryVO>.toCategoryUIModels(): List<CategoriesModel> {
    val categoryMap = this.groupBy { it.parentCategoryId }
    return categoryMap[null].orEmpty().map { parent ->
        CategoriesModel(
            id = parent.id,
            name = parent.name,
            subCategories = categoryMap[parent.id].orEmpty().map { child ->
                CategoriesModel(
                    id = child.id,
                    name = child.name
                )
            }
        )
    }
}