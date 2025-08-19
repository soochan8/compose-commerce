package com.chan.search.ui.mappers

import com.chan.search.domain.model.FilterCategoriesVO
import com.chan.search.ui.model.filter.FilterCategoriesModel
import com.chan.search.ui.model.filter.FilterCategoriesModel.SubCategoryUiModel

fun FilterCategoriesVO.toUiModel(): FilterCategoriesModel =
    FilterCategoriesModel(
        categoryId = this.categoryId,
        name = this.name,
        subCategories = this.subCategories.map {
            SubCategoryUiModel(
                subCategoryId = it.subCategoryId,
                subCategoryName = it.subCategoryName
            )
        }
    )

