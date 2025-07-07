package com.chan.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDetailNamesDto(
    val id: Int,
    val name: String,
    val sortOrder: Int
)
