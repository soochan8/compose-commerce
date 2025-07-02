package com.chan.category.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chan.category.ui.CategoryContract
import com.chan.category.ui.CategoryViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlin.math.abs


@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    val state by categoryViewModel.viewState.collectAsState()
    val effects = categoryViewModel.effect
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        categoryViewModel.setEvent(CategoryContract.Event.CategoriesLoad)
    }
    LaunchedEffect(effects) {
        effects.collect { effect ->
            when (effect) {
                is CategoryContract.Effect.ShowError -> Log.d(
                    "CategoryScreen",
                    " Error : ${effect.errorMessage}"
                )
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                //중앙에 가까운 Items
                val start = listState.layoutInfo.viewportStartOffset
                val end = listState.layoutInfo.viewportEndOffset
                val centerY = (start + end) / 2

                visibleItems.minByOrNull { info ->
                    val itemCenter = info.offset + info.size / 2
                    abs(itemCenter - centerY)
                }?.index
            }
            .mapNotNull { itemIndex ->
                // 헤더의 CategoryIndex 구함
                itemIndex?.let {
                    state.headerPositions
                        .filter { it.first <= itemIndex }
                        .maxByOrNull { it.first }
                        ?.second
                }
            }
            .distinctUntilChanged()
            .collect { newCatId ->
                categoryViewModel.setEvent(
                    CategoryContract.Event.SelectCategory(newCatId)
                )
            }
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight()
                .background(color = Color(0xFFF6F7F9))
        ) {
            items(items = state.categoryList, key = { it.id }) { category ->
                val selected = category.id == state.selectedCategoryId
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (selected) Color.White else Color.Transparent,
                        )
                        .clickable {
                            val targetIndex = state.headerPositions
                                .first { it.second == category.id }
                                .first

                            scope.launch {
                                listState.scrollToItem(
                                    index = targetIndex,
                                    scrollOffset = 0
                                )
                            }
                            categoryViewModel.setEvent(
                                CategoryContract.Event.SelectCategory(
                                    category.id
                                )
                            )
                        }
                        .padding(vertical = 12.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = category.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (selected) Color.Black else Color.Gray
                    )
                }
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(start = 20.dp)
        ) {
            state.categoryList.forEach { category ->
                category.subCategoryItems.forEach { subCategory ->
                    item(key = "header-${subCategory.id}") {
                        Text(
                            text = subCategory.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 25.dp)
                        )
                    }
                    items(
                        items = subCategory.items,
                        key = { it.id }
                    ) { subItem ->
                        Text(
                            text = subItem.name,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                        )
                    }
                }
            }
        }
    }
}