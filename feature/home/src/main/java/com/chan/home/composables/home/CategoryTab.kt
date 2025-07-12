package com.chan.home.composables.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chan.home.model.RankingCategoryModel

@Composable
fun CategoryTab(
    categories: List<RankingCategoryModel>,
    selectedCategoryTabIndex: Int,
    onSelectedChanged: (Int) -> Unit
) {


    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEachIndexed { index, category ->
            val isSelected = index == selectedCategoryTabIndex
            Surface(
                modifier = Modifier.clickable {
                    onSelectedChanged(index)
                },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) Color.Black else Color.White,
                border = BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) Color.Black else Color.Gray
                ),
            ) {
                Text(
                    text = category.rankingCategoryName,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) Color.White else Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}