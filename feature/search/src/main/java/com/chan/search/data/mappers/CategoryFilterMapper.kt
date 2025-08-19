package com.chan.search.data.mappers

import com.chan.database.dto.FilterCategoriesDto
import com.chan.search.domain.model.FilterCategoriesVO
import com.chan.search.domain.model.SubCategoryVO

fun FilterCategoriesDto.toCategoryFilterDomain(): FilterCategoriesVO =
    FilterCategoriesVO(
        categoryId = this.categoryId,
        name = this.name,
        subCategories = this.subCategories.map {
            SubCategoryVO(
                subCategoryId = it.subCategoryId,
                subCategoryName = it.subCategoryName
            )
        }
    )

