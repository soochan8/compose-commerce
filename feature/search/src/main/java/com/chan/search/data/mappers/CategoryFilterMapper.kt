package com.chan.search.data.mappers

import com.chan.database.entity.CommonCategoryEntity
import com.chan.search.domain.model.CategoryVO

fun CommonCategoryEntity.toCategoryVO(): CategoryVO =
    CategoryVO(
        id = id,
        name = name
    )



