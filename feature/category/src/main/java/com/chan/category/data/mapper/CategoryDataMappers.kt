package com.chan.category.data.mapper

import com.chan.category.domian.vo.CategoryVO
import com.chan.database.entity.category.CategoryEntity

fun CategoryEntity.toDomain(): CategoryVO {
    return CategoryVO(
        id = id,
        name = name,
        order = order,
        subCategoryItems = subCategoryItems.map { it.toDomain() }
    )
}

fun CategoryEntity.SubCategoryEntity.toDomain(): CategoryVO.SubCategoryVO {
    return CategoryVO.SubCategoryVO(
        id = id,
        name = name,
        order = order,
        items = items.map { it.toDomain() }
    )
}

fun CategoryEntity.SubCategoryEntity.SubCategoryItems.toDomain(): CategoryVO.SubCategoryVO.SubCategoryItems {
    return CategoryVO.SubCategoryVO.SubCategoryItems(
        id = id,
        name = name,
        order = order
    )
}