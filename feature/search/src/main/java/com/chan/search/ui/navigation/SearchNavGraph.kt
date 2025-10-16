package com.chan.search.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Routes
import com.chan.search.ui.composables.SearchScreen
import com.chan.search.ui.contract.SearchContract
import com.chan.search.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SearchNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = SearchDestination.route,
            arguments = SearchDestination.arguments
        ) { backStackEntry ->
            SearchRoute(navController)
        }
    }
}

@Composable
fun SearchRoute(navController: NavHostController) {
    val viewModel: SearchViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SearchContract.Effect.Navigation.ToCartRoute -> {
                    navController.navigate(
                        Routes.CART.route
                    )
                }

                is SearchContract.Effect.ShowError -> Log.d(
                    "SearchScreen",
                    " Error : ${effect.message}"
                )

                is SearchContract.Effect.Navigation.ToProductDetail -> {
                    navController.navigate(
                        Routes.PRODUCT_DETAIL.productDetailRoute(effect.productId)
                    )
                }

                SearchContract.Effect.Navigation.ToBackStack -> navController.popBackStack()
                SearchContract.Effect.ClearSearchFocus -> focusManager.clearFocus()
            }
        }
    }

    SearchScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}
