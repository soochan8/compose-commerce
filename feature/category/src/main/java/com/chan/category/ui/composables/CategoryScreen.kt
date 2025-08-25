package com.chan.category.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chan.android.ui.theme.CategoryBackground
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.appTypography
import com.chan.category.ui.CategoryContract
import com.chan.category.ui.CategoryViewModel
import com.chan.navigation.Routes
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch


@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    val state by categoryViewModel.viewState.collectAsState()
    val effects = categoryViewModel.effect
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val categoryState = rememberLazyListState()

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

    LaunchedEffect(state.selectedCategoryId) {
        val index = state.categories.indexOfFirst { it.id == state.selectedCategoryId }
        if (index != -1) {
            categoryState.animateScrollToItem(index)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { firstIndex ->
                // 현재 보이는 첫 아이템 기준으로 헤더 찾기
                state.headerPositions
                    .filter { it.first <= firstIndex }
                    .maxByOrNull { it.first }
                    ?.second
            }
            .mapNotNull { it }
            .distinctUntilChanged()
            .collect { newCatId ->
                categoryViewModel.setEvent(
                    CategoryContract.Event.SelectedCategory(newCatId)
                )
            }
    }



    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = categoryState,
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight()
                .background(color = CategoryBackground)
        ) {
            items(items = state.categories, key = { it.id }) { category ->
                val selected = category.id == state.selectedCategoryId
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (selected) Color.White else Color.Transparent,
                        )
                        .clickable {
                            val targetIndex = state.headerPositions
                                .firstOrNull { it.second == category.id }
                                ?.first

                            if (targetIndex != null) {
                                scope.launch {
                                    listState.scrollToItem(
                                        index = targetIndex,
                                        scrollOffset = 0
                                    )
                                }
                            }
                            categoryViewModel.setEvent(
                                CategoryContract.Event.SelectedCategory(
                                    category.id
                                )
                            )
                        }
                        .padding(vertical = 2.dp, horizontal = Spacing.spacing2)
                ) {
                    Text(
                        text = category.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Spacing.spacing3, horizontal = Spacing.spacing2),
                        style = MaterialTheme.appTypography.subCategoryTextStyle.copy(
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
                .padding(start = Spacing.spacing5)
        ) {
            state.categories.forEach { category ->
                item(key = "parent-${category.id}") {
                    Row(
                        modifier = Modifier.padding(top = Spacing.spacing6),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = category.imageUrl,
                            contentDescription = "상위 카테고리 이미지",
                            modifier = Modifier.size(Spacing.spacing7)
                        )
                        Spacer(modifier = Modifier.width(Spacing.spacing2))
                        Text(
                            text = category.name,
                            style = MaterialTheme.appTypography.categoryTextStyle,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        Routes.CATEGORY_DETAIL.categoryDetailRoute(category.id)
                                    )
                                }
                        )
                    }
                }
                category.subCategories.forEach { subCategory ->
                    item(key = "child-${subCategory.id}") {
                        Text(
                            text = subCategory.name,
                            style = MaterialTheme.appTypography.subCategoryTextStyle,
                            modifier = Modifier
                                .padding(top = Spacing.spacing6)
                                .clickable {
                                    navController.navigate(
                                        Routes.CATEGORY_DETAIL.categoryDetailRoute(subCategory.id)
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}