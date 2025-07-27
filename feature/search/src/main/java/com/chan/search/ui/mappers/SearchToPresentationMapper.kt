package com.chan.search.ui.mappers

import com.chan.domain.ProductVO
import com.chan.search.ui.model.SearchResultModel

fun ProductVO.toSearchModel(): SearchResultModel {
    return SearchResultModel(
        productId = productId,
        productName = productName
    )
}