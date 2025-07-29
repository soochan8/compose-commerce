package com.chan.category.navigation.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
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
            CategoryDetailScreen(
                categoryId = categoryId,
                onProductClick = { productId ->
                    navController.navigate(
                        Routes.PRODUCT_DETAIL.productDetailRoute(productId)
                    )
                }
            )
        }
    }
}