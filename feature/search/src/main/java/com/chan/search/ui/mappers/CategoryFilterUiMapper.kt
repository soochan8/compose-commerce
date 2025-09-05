package com.chan.search.ui.mappers

import com.chan.search.domain.model.CategoryVO
import com.chan.search.domain.model.FilterCategoryListVO
import com.chan.search.ui.model.filter.CategoryModel
import com.chan.search.ui.model.filter.FilterCategoryListModel

fun FilterCategoryListVO.toFilterCategoryModel(): FilterCategoryListModel {
    return FilterCategoryListModel(
        parent = parent.toPresentation(),
        children = children.map { it.toPresentation() }
    )
}

fun CategoryVO.toPresentation(): CategoryModel {
    return CategoryModel(
        id = id,
        name = name
    )
}


