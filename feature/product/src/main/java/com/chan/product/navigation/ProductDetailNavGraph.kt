package com.chan.product.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.chan.navigation.NavGraphProvider
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
                ProductDetailScreen(it)
            }
        }
    }
}