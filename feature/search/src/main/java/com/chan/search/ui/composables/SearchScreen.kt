package com.chan.search.ui.composables

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.chan.navigation.Routes
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when(effect) {
                SearchContract.Effect.Navigation.ToCartRoute -> navController.navigate(
                    Routes.CART.route
                )
                is SearchContract.Effect.ShowError -> Log.d(
                    "SearchScreen",
                    " Error : ${effect.message}"
                )
            }
        }
    }

    SearchScreenContent(
        navController = navController,
        state = state,
        onEvent = viewModel::setEvent
    )
}