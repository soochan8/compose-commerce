package com.chan.category.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.category.ui.CategoryContract
import com.chan.category.ui.CategoryViewModel
import com.chan.category.ui.composables.category.CategoryContent
import com.chan.category.ui.composables.category.CategorySidebar
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
    val contentListState = rememberLazyListState()
    val sidebarListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        categoryViewModel.effect.collect { effect ->
            when (effect) {
                is CategoryContract.Effect.ShowError -> Log.d(
                    "CategoryScreen",
                    " Error : ${effect.errorMessage}"
                )

                is CategoryContract.Effect.Navigation.ToCategoryDetail -> {
                    navController.navigate(
                        Routes.CATEGORY_DETAIL.categoryDetailRoute(effect.categoryId)
                    )
                }

                is CategoryContract.Effect.ScrollToSidebar -> {
                    sidebarListState.scrollToItem(effect.index)
                }
            }
        }
    }

    LaunchedEffect(contentListState) {
        snapshotFlow { contentListState.firstVisibleItemIndex }
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
                    CategoryContract.Event.OnCategorySidebarClick(newCatId)
                )
            }
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        CategorySidebar(
            categories = state.categories,
            selectedCategoryId = state.selectedCategoryId,
            listState = sidebarListState,
            onCategoryClick = { categoryId ->
                val targetIndex = state.headerPositions
                    .firstOrNull { it.second == categoryId }
                    ?.first

                if (targetIndex != null) {
                    scope.launch {
                        contentListState.scrollToItem(
                            index = targetIndex,
                            scrollOffset = 0
                        )
                    }
                }

                categoryViewModel.setEvent(
                    CategoryContract.Event.OnCategorySidebarClick(
                        categoryId
                    )
                )
            }
        )
        CategoryContent(
            categories = state.categories,
            state = contentListState,
            onCategoryClick = { categoryId ->
                categoryViewModel.setEvent(
                    CategoryContract.Event.OnCategoryClick(categoryId)
                )
            }
        )
    }
}