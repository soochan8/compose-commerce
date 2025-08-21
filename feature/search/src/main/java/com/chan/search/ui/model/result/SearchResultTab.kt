package com.chan.search.ui.model.result

sealed class SearchResultTab(val title: String, val index: Int) {
    object Product: SearchResultTab("상품", 0)
    object Review: SearchResultTab("후기", 1)
    object Content: SearchResultTab("콘텐츠", 2)

    companion object {
        val allTabs: List<SearchResultTab> = listOf(Product, Review, Content)
    }
}