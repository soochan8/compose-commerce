package com.chan.category.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.category.ui.CategoryContract
import com.chan.category.ui.CategoryViewModel
import com.chan.category.ui.composables.category.CategoryContent
import com.chan.category.ui.composables.category.CategorySidebar
import com.chan.navigation.Routes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {

    val state by categoryViewModel.viewState.collectAsState()
    val contentListState = rememberLazyListState()
    val sidebarListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        categoryViewModel.effect.collectLatest { effect ->
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

                is CategoryContract.Effect.ScrollToContent -> {
                    contentListState.scrollToItem(
                        effect.index
                    )
                }
            }
        }

    }

    LaunchedEffect(contentListState) {
        snapshotFlow { contentListState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { firstIndex ->
                categoryViewModel.setEvent(CategoryContract.Event.OnContentScroll(firstIndex))
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