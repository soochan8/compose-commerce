package com.chan.category.data.mapper

import com.chan.category.domain.dto.CategoriesDto
import com.chan.category.domain.vo.CategoryVO
import com.chan.database.entity.CommonCategoryEntity
import com.chan.database.entity.CommonProductEntity
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

fun CommonCategoryEntity.toCategoryVO() : CategoryVO {
    return CategoryVO(
        id = id,
        name = name,
        parentCategoryId = parentCategoryId
    )
}