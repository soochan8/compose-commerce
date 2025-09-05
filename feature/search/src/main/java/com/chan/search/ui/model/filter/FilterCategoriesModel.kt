package com.chan.search.ui.model.filter

data class FilterCategoryListModel(
    val parent: CategoryModel,
    val children: List<CategoryModel>,
    val isExpanded: Boolean = false // UI에서 열림/닫힘 상태
)

data class CategoryModel(
    val id: String,
    val name: String,
    val isSelected: Boolean = false // 필터 선택 여부
)
