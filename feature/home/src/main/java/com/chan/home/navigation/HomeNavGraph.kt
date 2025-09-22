package com.chan.home.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.home.composables.HomeScreen
import com.chan.home.home.HomeContract
import com.chan.home.home.HomeViewModel
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Routes
import javax.inject.Inject

class HomeNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(HomeDestination.route) {
            HomeRoute(navController)
        }
    }
}

@Composable
fun HomeRoute(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setEvent(HomeContract.Event.BannerLoad)
        viewModel.setEvent(HomeContract.Event.PopularItemLoad)
        viewModel.setEvent(HomeContract.Event.RankingCategoryTabsLoad)
        viewModel.setEvent(HomeContract.Event.SaleProducts)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.Navigation.ToProductDetailRoute ->
                    navController.navigate(
                        Routes.PRODUCT_DETAIL.productDetailRoute(effect.productId)
                    )

                is HomeContract.Effect.ShowError -> Toast.makeText(
                    context,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
                is HomeContract.Effect.Navigation.ToCartPopupRoute -> {
                    navController.navigate(
                        Routes.CART_POPUP.cartPopUpRoute(effect.productId)
                    )
                }
                is HomeContract.Effect.Navigation.ToCartRoute -> {
                    navController.navigate(
                        Routes.CART.route
                    )
                }

                HomeContract.Effect.Navigation.ToSearchRoute -> {
                    navController.navigate(
                        Routes.SEARCH.route
                    )
                }
            }
        }
    }

    HomeScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}