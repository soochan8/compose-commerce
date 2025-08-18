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
import com.chan.android.model.ProductModel
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.android.ui.theme.dividerColor
import com.chan.android.ui.theme.Black
import com.chan.navigation.Routes
import com.chan.search.R
import com.chan.search.ui.composables.result.SearchResultScreen
import com.chan.search.ui.model.SearchHistoryModel
import com.chan.search.ui.model.SearchResultModel
import com.chan.search.ui.model.TrendingSearchModel
import com.chan.search.ui.model.filter.DeliveryOption

@Composable
fun SearchScreenContent(
    navController: NavHostController,
    search: String,
    recentSearches: List<SearchHistoryModel>,
    recommendedKeywords: List<String>,
    trendingSearches: List<TrendingSearchModel>,
    searchResults: List<SearchResultModel>,
    currentTime: String,
    showSearchResult: Boolean,
    searchResultProducts: List<ProductModel>,
    showFilter: Boolean,
    selectedDeliveryOption: DeliveryOption?,
    onSearchChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onSearchClick: (String) -> Unit,
    onSearchTextFocus: () -> Unit,
    onRemoveSearchKeyword: (String) -> Unit,
    onClearAllRecentSearches: () -> Unit,
    onSearchResultItemClick: (String) -> Unit,
    onClickBack: () -> Unit,
    onClickCart: () -> Unit,
    onUpdateFilterClick: () -> Unit,
    onDeliveryOptionClick: (DeliveryOption) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = White,
            topBar = {
                SearchTopAppBar(
                    onClickBack = onClickBack,
                    onClickCart = onClickCart
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                SearchTextField(
                    search = search,
                    onSearchChanged = onSearchChanged,
                    onClearSearch = onClearSearch,
                    onSearchClick = {
                        onSearchClick(it)
                    },
                    onSearchTextFocus = {
                        onSearchTextFocus()
                    },
                    modifier = Modifier.padding(Spacing.spacing4)
                )
                HorizontalDivider(color = dividerColor, thickness = 1.dp)

                if (!showSearchResult) {
                    if (search.isBlank()) {
                        if (recentSearches.isNotEmpty()) {
                            RecentSearchList(
                                recentSearches = recentSearches,
                                onRemoveSearch = onRemoveSearchKeyword,
                                onClearAllRecentSearches = onClearAllRecentSearches,
                                onSearchClick = {
                                    onSearchClick(it)
                                },
                            )
                        }
                        RecommendedKeywordList(recommendedKeywords = recommendedKeywords)
                        TrendingSearchList(
                            trendingSearches = trendingSearches,
                            currentTime = currentTime
                        )
                    } else {
                        SearchResultList(
                            results = searchResults,
                            searchQuery = search,
                            onSearchResultItemClick = onSearchResultItemClick
                        )
                    }
                } else {
                    if (searchResultProducts.isEmpty()) {
                        Text(text = stringResource(R.string.search_empty_product))
                    } else {
                        Box(modifier = Modifier.weight(1f)) {
                            SearchResultScreen(
                                products = searchResultProducts,
                                onNavigateToFilter = onUpdateFilterClick,
                                onProductClick = { productId ->
                                    navController.navigate(
                                        Routes.PRODUCT_DETAIL.productDetailRoute(productId)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showFilter,
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
                        onClick = onUpdateFilterClick
                    )
            )
        }

        AnimatedVisibility(
            visible = showFilter,
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
                    selectedDeliveryOption = selectedDeliveryOption,
                    onClose = onUpdateFilterClick,
                    onDeliveryOptionClick = onDeliveryOptionClick,
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