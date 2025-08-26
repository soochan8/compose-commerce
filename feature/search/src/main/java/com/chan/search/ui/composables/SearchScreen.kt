package com.chan.search.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.search.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    SearchScreenContent(
        navController = navController,
        state = state,
        onEvent = viewModel::setEvent
    )
}