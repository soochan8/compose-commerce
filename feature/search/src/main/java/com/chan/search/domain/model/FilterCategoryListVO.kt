package com.chan.search.domain.model

data class FilterCategoryListVO(
    val parent: CategoryVO,
    val children: List<CategoryVO>
)

data class CategoryVO(
    val id: String,
    val name: String
)