package com.chan.category.data.mapper

import com.chan.category.domain.dto.CategoriesDto
import com.chan.database.entity.ProductEntity

fun ProductEntity.toCategoriesDto(): CategoriesDto {
    return CategoriesDto(
        id = id,
        name = name,
        categories = categories.map { it.toCategoriesDto() }
    )
}

fun ProductEntity.Categories.toCategoriesDto(): CategoriesDto.Categories {
    return CategoriesDto.Categories(
        id = id,
        name = name,
        subCategories = subCategories.map { it.toCategoriesDto() }
    )
}

fun ProductEntity.Categories.SubCategories.toCategoriesDto(): CategoriesDto.Categories.SubCategories {
    return CategoriesDto.Categories.SubCategories(
        categoryId = categoryId,
        categoryName = categoryName
    )
}