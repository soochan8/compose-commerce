package com.chan.product.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.navigation.NavGraphProvider
import com.chan.product.ui.ProductDetailViewModel
import com.chan.product.ui.composables.ProductDetailScreen
import javax.inject.Inject

class ProductDetailNavGraph @Inject constructor() : NavGraphProvider {
    override fun addGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = ProductDetailDestination.route,
            arguments = ProductDetailDestination.arguments
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            productId?.let {
                ProductDetailRoute(it, navController)

            }
        }
    }
}

@Composable
fun ProductDetailRoute(productId: String, navController: NavHostController) {
    val viewModel: ProductDetailViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    ProductDetailScreen(
        productId = productId,
        state = state,
        onEvent = viewModel::setEvent,
        effect = viewModel.effect
    )
}