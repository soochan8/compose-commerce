package com.chan.search.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chan.android.model.ProductModel
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.android.ui.theme.dividerColor
import com.chan.search.R
import com.chan.search.ui.model.SearchHistoryModel
import com.chan.search.ui.model.SearchResultModel
import com.chan.search.ui.model.TrendingSearchModel

@Composable
fun SearchScreenContent(
    search: String,
    recentSearches: List<SearchHistoryModel>,
    recommendedKeywords: List<String>,
    trendingSearches: List<TrendingSearchModel>,
    searchResults: List<SearchResultModel>,
    currentTime: String,
    showSearchResult : Boolean,
    searchResultProducts : List<ProductModel>,
    onSearchChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    onSearchClick: () -> Unit,
    onRemoveSearchKeyword: (String) -> Unit,
    onClearAllRecentSearches: () -> Unit,
    onSearchResultItemClick: (String) -> Unit,
    onClickBack: () -> Unit,
    onClickCart: () -> Unit,
) {
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
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            SearchTextField(
                search = search,
                onSearchChanged = onSearchChanged,
                onClearSearch = onClearSearch,
                onSearchClick = onSearchClick,
                modifier = Modifier.padding(Spacing.spacing4)
            )
            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            if(!showSearchResult) {
                if (search.isBlank()) {
                    if (recentSearches.isNotEmpty()) {
                        RecentSearchList(
                            recentSearches = recentSearches,
                            onRemoveSearch = onRemoveSearchKeyword,
                            onClearAllRecentSearches = onClearAllRecentSearches,
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