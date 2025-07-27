package com.chan.search.ui.composables

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chan.android.ui.theme.Spacing
import com.chan.android.ui.theme.White
import com.chan.android.ui.theme.appTypography
import com.chan.android.ui.theme.dividerColor
import com.chan.search.R
import com.chan.search.ui.SearchContract
import com.chan.search.ui.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onClickBack: () -> Unit,
    onClickCart: () -> Unit,
) {
    val state by viewModel.viewState.collectAsState()


    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {

                is SearchContract.Effect.ShowError -> {
                    Log.d("SearchScreen", "error : ${effect.message}")
                }
            }
        }
    }

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
                search = state.search,
                onSearchChanged = { viewModel.setEvent(SearchContract.Event.OnSearchChanged(it)) },
                onClearSearch = { viewModel.setEvent(SearchContract.Event.OnClickClearSearch) },
                onSearchClick = { viewModel.setEvent(SearchContract.Event.OnClickSearch) },
                modifier = Modifier.padding(Spacing.spacing4)
            )
            HorizontalDivider(color = dividerColor, thickness = 1.dp)

            if (state.search.isBlank()) {
                //추후 구현 예정

            } else {
                SearchResultList(
                    results = state.searchResults,
                    searchQuery = state.search,
                    onSearchResultItemClick = {
                        viewModel.setEvent(SearchContract.Event.OnClickSearchResult(search = state.search))
                    }
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