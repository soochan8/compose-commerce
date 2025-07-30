package com.chan.search.ui.mappers

import com.chan.domain.ProductVO
import com.chan.search.domain.model.SearchHistoryVO
import com.chan.search.domain.model.TrendingSearchVO
import com.chan.search.ui.model.SearchHistoryModel
import com.chan.search.ui.model.SearchResultModel
import com.chan.search.ui.model.TrendingSearchModel

fun ProductVO.toSearchModel(): SearchResultModel {
    return SearchResultModel(
        productId = productId,
        productName = productName
    )
}

fun SearchHistoryVO.toSearchHistoryModel() : SearchHistoryModel {
    return SearchHistoryModel(
        search = search
    )
}

fun TrendingSearchVO.toTrendingSearchModel() : TrendingSearchModel {
    return TrendingSearchModel(
        rank = rank,
        keyword = keyword,
        change = change
    )
}