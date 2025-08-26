package com.chan.category.ui.composables.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.CategoryBackground
import com.chan.android.ui.theme.Gray
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.category.ui.model.CategoriesModel

@Composable
fun CategorySidebar(
    categories: List<CategoriesModel>,
    selectedCategoryId: String?,
    listState: LazyListState,
    onCategoryClick: (categoryId: String) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth(0.33f)
            .fillMaxHeight()
            .background(color = CategoryBackground)
    ) {
        items(categories, key = { it.id }) { category ->
            CategorySidebarItem(
                category = category,
                isSelected = category.id == selectedCategoryId,
                onClick = { onCategoryClick(category.id) }
            )
        }
    }
}

@Composable
fun CategorySidebarItem(
    category: CategoriesModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) White else Transparent)
            .clickable { onClick() }
            .padding(vertical = 2.dp, horizontal = Spacing.spacing2)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.appTypography.subCategoryTextStyle.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (isSelected) Black else Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.spacing3, horizontal = Spacing.spacing2)
        )
    }
}