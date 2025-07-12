package com.chan.home.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeTopTab(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(selectedTabIndex) {
        lazyListState.animateScrollToItem(selectedTabIndex)
    }

    Column(modifier = modifier.background(Color.White)) {
        LazyRow(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(items = tabs, key = { index, _ -> index }) { index, title ->
                CustomTab(
                    title = title,
                    isSelected = index == selectedTabIndex,
                    onClick = { onTabClick(index) }
                )
            }
        }
        HorizontalDivider(color = Color(0xFF_D9_DC_E3), thickness = 1.dp)
    }
}