package com.chan.category.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.android.ui.composable.CommonTopBar
import com.chan.android.ui.composable.MainTopBar
import com.chan.android.ui.theme.Spacing
import com.chan.category.ui.CategoryContract
import com.chan.category.ui.CategoryViewModel
import com.chan.category.ui.composables.category.CategoryContent
import com.chan.category.ui.composables.category.CategorySidebar
import com.chan.navigation.Routes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class)
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

                CategoryContract.Effect.Navigation.ToSearchRoute -> {
                    navController.navigate(
                        Routes.SEARCH.route
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

    val topTabs = remember { listOf("올리브영", "헬스+", "Luxe Edit") }

    Scaffold(
        topBar = {
            MainTopBar(
                navigationIcon = null,
                titleContent = {
                    CategoryTopTabs(
                        tabs = topTabs,
                        selectedIndex = 0,
                        onTabClick = { index ->

                        }
                    )
                },
                actions = {
                    IconButton(onClick = {
                        categoryViewModel.setEvent(CategoryContract.Event.OnSearchClick)
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "검색")
                    }
                }
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
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
}

@Composable
fun CategoryTopTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabClick: (Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.spacing4)) {
        tabs.forEachIndexed { index, title ->
            CommonTopBar(
                title = title,
                isSelected = index == selectedIndex,
                onClick = { onTabClick(index) }
            )
        }
    }
}