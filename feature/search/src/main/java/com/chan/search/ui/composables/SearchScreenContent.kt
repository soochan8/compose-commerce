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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chan.android.ui.theme.Black
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.android.ui.theme.dividerColor
import com.chan.navigation.Routes
import com.chan.search.R
import com.chan.search.ui.composables.result.SearchResultScreen
import com.chan.search.ui.contract.SearchContract

@Composable
fun SearchScreenContent(
    navController: NavHostController,
    state: SearchContract.State,
    onEvent: (SearchContract.Event) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = White,
            topBar = {
                SearchTopAppBar(
                    onClickBack = { navController.popBackStack() },
                    onClickCart = { }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                SearchTextField(
                    search = state.search,
                    onSearchChanged = { onEvent(SearchContract.Event.OnSearchChanged(it)) },
                    onClearSearch = { onEvent(SearchContract.Event.OnClickClearSearch) },
                    onSearchClick = { onEvent(SearchContract.Event.OnAddSearchKeyword(it)) },
                    onSearchTextFocus = { onEvent(SearchContract.Event.OnSearchTextFocus) },
                    modifier = Modifier.padding(Spacing.spacing4)
                )
                HorizontalDivider(color = dividerColor, thickness = 1.dp)

                when {
                    state.showSearchResult -> {
                        if (state.searchResultProducts.isEmpty()) {
                            Text(text = stringResource(R.string.search_empty_product))
                        } else {
                            Box(modifier = Modifier.weight(1f)) {
                                SearchResultScreen(
                                    state = state,
                                    onEvent = onEvent,
                                    onProductClick = { productId ->
                                        navController.navigate(
                                            Routes.PRODUCT_DETAIL.productDetailRoute(productId)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    state.search.isBlank() -> {
                        if (state.recentSearches.isNotEmpty()) {
                            RecentSearchList(
                                recentSearches = state.recentSearches,
                                onRemoveSearch = {
                                    onEvent(
                                        SearchContract.Event.OnRemoveSearchKeyword(
                                            it
                                        )
                                    )
                                },
                                onClearAllRecentSearches = { onEvent(SearchContract.Event.OnClearAllRecentSearches) },
                                onSearchClick = { onEvent(SearchContract.Event.OnAddSearchKeyword(it)) },
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
                            onSearchResultItemClick = {
                                onEvent(
                                    SearchContract.Event.OnClickSearchProduct(
                                        it
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = state.showFilter,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onEvent(SearchContract.Event.OnUpdateFilterClick) }
                    )
            )
        }

        AnimatedVisibility(
            visible = state.showFilter,
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
                    state = state,
                    onEvent = onEvent,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun SearchTopAppBar(
    onClickBack: () -> Unit,
    onClickCart: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing4, vertical = Spacing.spacing2)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back",
            modifier = Modifier.clickable(
                onClick = onClickBack
            )
        )

        Text(
            text = stringResource(R.string.search),
            style = MaterialTheme.appTypography.searchTitle,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "cart",
            modifier = Modifier.clickable(
                onClick = onClickCart
            )
        )
    }
}