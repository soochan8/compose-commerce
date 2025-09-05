package com.chan.search.ui.composables.result.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.DarkGray
import com.chan.android.ui.theme.LightGray
import com.chan.android.ui.theme.Radius
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.search.ui.model.FilterChipType
import com.chan.search.ui.model.SearchResultFilterChipModel
import kotlin.collections.forEach

@Composable
fun FilterChipRow(
    filters: List<SearchResultFilterChipModel>,
    onToggleFilter: (SearchResultFilterChipModel) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing2),
            horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            filters.forEach { filter ->
                FilterChip(
                    filter = filter,
                    onToggleFilter = onToggleFilter,
                    onFilterClick = onFilterClick,
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    filter: SearchResultFilterChipModel,
    onToggleFilter: (SearchResultFilterChipModel) -> Unit,
    onFilterClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val chipCornerShape = RoundedCornerShape(Radius.radius6)
    val (containerColor, textColor) = if (filter.isSelected) {
        Black to Color.White
    } else {
        White to DarkGray
    }

    Box(
        modifier = Modifier
            .background(color = containerColor, shape = chipCornerShape)
            .clip(chipCornerShape)
            .border(width = 1.dp, color = LightGray, shape = chipCornerShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    when (filter.chipType) {
                        FilterChipType.TOGGLE -> onToggleFilter(filter)
                        FilterChipType.DROP_DOWN -> onFilterClick()
                    }
                }
            )
            .padding(horizontal = Spacing.spacing3, vertical = Spacing.spacing2)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            when (filter.chipType) {
                FilterChipType.TOGGLE -> ToggleChipContent(filter, textColor)
                FilterChipType.DROP_DOWN -> DropDownChipContent(filter, textColor)
            }
        }
    }
}

@Composable
private fun ToggleChipContent(filter: SearchResultFilterChipModel, textColor: Color) {
    AsyncImage(
        model = filter.image,
        contentDescription = filter.text,
        modifier = Modifier
            .size(Spacing.spacing5)
            .padding(end = Spacing.spacing1)
    )
    Text(text = filter.text, color = textColor)
}

@Composable
private fun DropDownChipContent(filter: SearchResultFilterChipModel, textColor: Color) {
    Text(
        text = filter.text,
        color = textColor,
        modifier = Modifier.padding(end = Spacing.spacing1)
    )
    Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = "Dropdown",
        tint = textColor,
        modifier = Modifier.size(Spacing.spacing4)
    )
}