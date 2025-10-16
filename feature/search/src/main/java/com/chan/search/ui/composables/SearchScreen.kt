package com.chan.search.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chan.android.LoadingState
import com.chan.android.ui.composable.MainTopBar
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.dividerColor
import com.chan.search.R
import com.chan.search.ui.actions.SearchActions
import com.chan.search.ui.composables.result.SearchResultScreen
import com.chan.search.ui.contract.SearchContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchContract.State,
    onEvent: (SearchContract.Event) -> Unit
) {
    val actions = remember(onEvent) { SearchActions(onEvent) }
    val isFilterVisible = state.filter.showFilter
    val overlayInteraction = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = White,
            topBar = {
                MainTopBar(
                    navigationIcon = {
                        IconButton(onClick = actions.onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                        }
                    },
                    titleContent = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.search))
                        }
                    },
                    actions = {
                        IconButton(onClick = actions.onCartClick) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "장바구니")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                SearchTextField(
                    search = state.search,
                    onSearchChanged = actions::onSearchChanged,
                    onClearSearch = actions.onClearSearch,
                    onSearchClick = actions::onSearchClick,
                    onSearchTextFocus = actions.onSearchTextFocus,
                    modifier = Modifier.padding(Spacing.spacing4)
                )
                HorizontalDivider(color = dividerColor, thickness = 1.dp)

                when {
                    state.showSearchResult -> {
                        if (state.searchResultProducts.isEmpty() && state.loadingState == LoadingState.Success) {
                            Text(text = stringResource(R.string.search_empty_product))
                        } else {
                            Box(modifier = Modifier.weight(1f)) {
                                SearchResultScreen(
                                    state = state,
                                    onEvent = onEvent,
                                    onProductClick = actions::onProductClick
                                )
                            }
                        }
                    }

                    state.search.isBlank() -> {
                        if (state.recentSearches.isNotEmpty()) {
                            RecentSearchList(
                                recentSearches = state.recentSearches,
                                onRemoveSearch = actions::onRemoveSearchKeyword,
                                onClearAllRecentSearches = actions.onClearAllRecentSearches,
                                onSearchClick = actions::onAddSearchKeyword
                            )
                        }
                        RecommendedKeywordList(recommendedKeywords = state.recommendedKeywords)
                        TrendingSearchList(
                            trendingSearches = state.trendingSearches,
                            currentTime = state.currentTime
                        )
                    }


                    else -> {
                        SearchResultList(
                            results = state.searchResults,
                            searchQuery = state.search,
                            onSearchResultItemClick = actions::onSearchResultItemClick
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isFilterVisible,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = overlayInteraction,
                        indication = null,
                        onClick = actions.filter.onFilterClick
                    )
            )
        }

        AnimatedVisibility(
            visible = isFilterVisible,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                SearchFilterScreen(
                    filterState = state.filter,
                    actions = actions.filter,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight()
                )
            }
        }
    }
}