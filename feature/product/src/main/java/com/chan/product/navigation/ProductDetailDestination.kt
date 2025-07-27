package com.chan.product.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chan.navigation.NavDestination
import com.chan.navigation.Routes

object ProductDetailDestination : NavDestination {
    override val route = Routes.PRODUCT_DETAIL.route
    override val arguments = listOf(
        navArgument("productId") { type = NavType.StringType }
    )
}