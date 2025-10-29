package com.chan.category.navigation.detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.category.ui.CategoryDetailContract
import com.chan.category.ui.CategoryDetailViewModel
import com.chan.category.ui.composables.detail.CategoryDetailScreen
import com.chan.navigation.NavGraphProvider
import com.chan.navigation.Routes
import javax.inject.Inject

class CategoryDetailNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = CategoryDetailDestination.route,
            arguments = CategoryDetailDestination.arguments
        ) { backStackEntry ->
            val categoryId = requireNotNull(backStackEntry.arguments?.getString("categoryId")) {
                "categoryId is required"
            }

            CategoryDetailRoute(categoryId, navController)
        }
    }
}

@Composable
fun CategoryDetailRoute(categoryId: String, navController: NavHostController) {
    val viewModel: CategoryDetailViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CategoryDetailContract.Effect.Navigation.ToProductDetail -> {
                    navController.navigate(
                        Routes.PRODUCT_DETAIL.productDetailRoute(effect.productId)
                    )
                }

                is CategoryDetailContract.Effect.ShowError -> {
                    Log.e("CategoryDetailError", "${effect.errorMessage}")
                }

                is CategoryDetailContract.Effect.Navigation.ToCartPopupRoute -> navController.navigate(Routes.CART_POPUP.cartPopUpRoute(effect.productId))
            }
        }
    }

    // 초기 로딩 이벤트
    LaunchedEffect(categoryId) {
        viewModel.setEvent(CategoryDetailContract.Event.CategoryDetailLoad(categoryId))
    }

    CategoryDetailScreen(
        state = state,
        onEvent = viewModel::setEvent,
    )
}