package com.chan.category.domain.vo


data class CategoryVO(
    val id: String,
    val name: String,
    val parentCategoryId: String?
)