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
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chan.home.composables.HomeScreen
import com.chan.home.composables.home.banner.HomeBannerWebViewScreen
import com.chan.home.home.HomeContract
import com.chan.home.home.HomeViewModel
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Routes
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

class HomeNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(HomeDestination.route) {
            HomeRoute(navController)
        }

        navGraphBuilder.composable(
            route = Routes.HOME_BANNER_WEB_VIEW.route,
            arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""

            val viewModel: HomeViewModel = hiltViewModel()

            HomeBannerWebViewScreen(
                url = url,
                onEvent = viewModel::setEvent,
                effect = viewModel.effect
            )
        }
    }
}

@Composable
fun HomeRoute(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.setEvent(HomeContract.Event.Banner(HomeContract.BannerEvent.LoadBanners))
        viewModel.setEvent(HomeContract.Event.PopularItemLoad)
        viewModel.setEvent(HomeContract.Event.RankingCategoryTabsLoad)
        viewModel.setEvent(HomeContract.Event.SaleProducts)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect
            .filterIsInstance<HomeContract.Effect.Navigation>()
            .collect { navEffect ->
                when (navEffect) {
                    is HomeContract.Effect.Navigation.ToProductDetailRoute ->
                        navController.navigate(
                            Routes.PRODUCT_DETAIL.productDetailRoute(navEffect.productId)
                        )

                    is HomeContract.Effect.Navigation.ToCartPopupRoute ->
                        navController.navigate(Routes.CART_POPUP.cartPopUpRoute(navEffect.productId))

                    is HomeContract.Effect.Navigation.ToCartRoute ->
                        navController.navigate(Routes.CART.route)

                    HomeContract.Effect.Navigation.ToSearchRoute ->
                        navController.navigate(Routes.SEARCH.route)

                    is HomeContract.Effect.Navigation.ToWebView ->
                        navController.navigate(
                            Routes.HOME_BANNER_WEB_VIEW.homeBannerWebViewRoute(navEffect.url)
                        )
                }
            }
    }


    LaunchedEffect(viewModel.effect) {
        viewModel.effect
            .filterIsInstance<HomeContract.Effect.ShowError>()
            .collect { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
    }

    HomeScreen(
        state = state,
        onEvent = viewModel::setEvent
    )
}