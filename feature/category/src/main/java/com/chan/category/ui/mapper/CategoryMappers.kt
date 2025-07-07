package com.chan.category.ui.mapper

import com.chan.category.domian.vo.CategoryVO
import com.chan.category.ui.model.CategoryModel

fun CategoryVO.toPresentation(): CategoryModel {
    return CategoryModel(
        id = id,
        name = name,
        subCategoryItems = subCategoryItems.map { it.toPresentation() }
    )
}

fun CategoryVO.SubCategoryVO.toPresentation(): CategoryModel.SubCategoryModel {
    return CategoryModel.SubCategoryModel(
        id = id,
        name = name,
        items = items.map { it.toPresentation() }
    )
}

fun CategoryVO.SubCategoryVO.SubCategoryItems.toPresentation(): CategoryModel.SubCategoryModel.SubCategoryItems {
    return CategoryModel.SubCategoryModel.SubCategoryItems(
        id = id,
        name = name,
        order = order
    )
}